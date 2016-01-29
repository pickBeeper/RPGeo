package rpgeo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

public class Place extends BasicTickable {
   private World myWorld;
   private int myWidth;
   private int myHeight;
   private String myName;
   private Rectangle myBounds;
   private LinkedList<Tickable> myComponents;
   private Grid myGrid;

   public Place(World world, int width, int height, String name) {
      myWorld = world;
      myWidth = width;
      myHeight = height;
      myName = name;
      myBounds = myWorld.getBounds();
      myComponents = new LinkedList<Tickable>();
      myGrid = new Grid(myBounds, myWidth, myHeight);
      myComponents.add(myGrid);
   }

   @Override
   public void draw(Graphics pen) {
      for ( Tickable toDraw : myComponents ) {
         toDraw.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( Tickable toUpdate : myComponents ) {
         toUpdate.update();
      }
   }

   @Override
   public void tick() {
      for ( Tickable toTick : myComponents ) {
         toTick.tick();
      }
   }

   @Override
   public void addComponent(Tickable toAdd) {
      myComponents.add(toAdd);
   }

   @Override
   public void removeComponent(Tickable toRemove) {
      myComponents.remove(toRemove);
   }

   @Override
   public Collection<Tickable> getComponents() {
      return myComponents;
   }

   public World getWorld() { return myWorld; }
   public String getName() { return myName; }
   public Grid getGrid() { return myGrid; }
}