package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

import gfm.DrawUpdatable;

public class Tile extends BasicTickable {
   private Grid myGrid;
   private Rectangle myRect;
   private int myRow;
   private int myCol;
   private Color myBackground;
   private LinkedList<Tickable> myChildren;

   public Tile(Grid grid, Rectangle rect, int row, int col, Color background) {
      myGrid = grid;
      myRect = rect;
      myRow = row;
      myCol = col;
      myBackground = background;
      myChildren = new LinkedList<Tickable>();
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(myBackground);
      pen.fillRect(myRect.x, myRect.y, myRect.width, myRect.height);

      for ( DrawUpdatable child : myChildren ) {
         child.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( DrawUpdatable child : myChildren ) {
         child.update();
      }
   }

   @Override
   public void tick() {
      for ( Tickable toTick : myChildren ) {
         toTick.tick();
      }
   }

   public Rectangle getRect() { return myRect; }
   public void setRect(Rectangle rect) { myRect = rect; }


   @Override
   public void addComponent(Tickable toAdd) {
      myChildren.add(toAdd);
   }

   @Override
   public void removeComponent(Tickable toRemove) {
      myChildren.remove(toRemove);
   }

   @Override
   public Collection<Tickable> getComponents() { return myChildren; }
   public Grid getGrid() { return myGrid; }
   public int getRow() { return myRow; }
   public int getCol() { return myCol; }

}