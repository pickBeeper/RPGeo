package rpgeo;

import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import gfm.GameComponent;

public class Place implements GameComponent<DrawUpdatable> {
   private World myWorld;
   private String myName;
   private LinkedList<DrawUpdatable> myComponents;

   public Place(World world, String name) {
      myWorld = world;
      myName = name;
      myComponents = new LinkedList<DrawUpdatable>();
   }

   @Override
   public void draw(Graphics pen) {
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
}
