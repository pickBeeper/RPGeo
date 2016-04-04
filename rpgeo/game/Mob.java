package rpgeo.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import rpgeo.Grid;
import rpgeo.Tickable;
import rpgeo.Tile;

public abstract class Mob extends BasicTickable {
   private LinkedList<Tickable> myComponents = new LinkedList<Tickable>();
   private Tile myTile;
   private String myName;

   public boolean move(int x, int y) {
      // check in bounds
      int newRow = myTile.getRow() + y;
      int newCol = myTile.getCol() + x;
      if ( !myTile.getGrid().inBounds(newRow, newCol) ) {
         return false;
      }

      clearQueuedType("move");

      Tile target = myTile.getGrid().getTile(newRow, newCol);
      MoveAction toQueue = new MoveAction(this, myTile, target);
      queueForTick("move", toQueue);

      return true;
   }


   @Override
   public void addComponent(Tickable toAdd) {
      myComponents.add(toAdd);
   }

   /* (non-Javadoc)
    * @see gfm.GameComponent#removeComponent(java.lang.Object)
    */
   @Override
   public void removeComponent(Tickable toRemove) {
      myComponents.remove(toRemove);
   }

   /* (non-Javadoc)
    * @see gfm.GameComponent#getComponents()
    */
   @Override
   public Collection<Tickable> getComponents() { return myComponents; }
   public Tile getTile() { return myTile; }
   public void setTile(Tile tile) { myTile = tile; }
   public Grid getGrid() { return myTile.getGrid(); }
   public String getName() { return myName; }
   public void setName(String name) { myName = name; }
}

class MoveAction implements ActionListener {
   private Mob myMob;
   private Tile myFrom;
   private Tile myTo;

   public MoveAction(Mob mob, Tile from, Tile to) {
      myMob = mob;
      myFrom = from;
      myTo = to;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if ( myTo.isPassable() ) {
         myMob.setTile(myTo);
         myFrom.getComponents().remove(myMob);
         myTo.addComponent(myMob);
      }
   }
}