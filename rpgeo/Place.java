package rpgeo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import gfm.GameComponent;

public class Place implements GameComponent<DrawUpdatable> {
   private World myWorld;
   private int myWidth;
   private int myHeight;
   private String myName;
   private Rectangle myBounds;
   private LinkedList<DrawUpdatable> myComponents;
   private Grid myGrid;

   public Place(World world, int width, int height, String name) {
      myWorld = world;
      myWidth = width;
      myHeight = height;
      myName = name;
      myBounds = myWorld.getBounds();
      myComponents = new LinkedList<DrawUpdatable>();
      myGrid = new Grid(myBounds, myWidth, myHeight);
   }

   @Override
   public void draw(Graphics pen) {
      myGrid.draw(pen);

      for ( DrawUpdatable toDraw : myComponents ) {
         toDraw.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( DrawUpdatable toUpdate : myComponents ) {
         toUpdate.update();
      }
   }

   @Override
   public void addComponent(DrawUpdatable toAdd) {
      myComponents.add(toAdd);
   }

   @Override
   public void removeComponent(DrawUpdatable toRemove) {
      myComponents.remove(toRemove);
   }

   @Override
   public Collection<DrawUpdatable> getComponents() {
      return myComponents;
   }

   public World getWorld() { return myWorld; }
   public String getName() { return myName; }
   public Grid getGrid() { return myGrid; }
}