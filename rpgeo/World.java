package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;
import java.util.HashMap;

import gfm.GameComponent;
import gfm.gui.GUIManager;

public class World implements GameComponent<Place> {
   private GUIManager myGUIManager;
   private Rectangle myBounds;
   private Player myPlayer;
   private MouseGoto myMouseGoto;
   private HashMap<String, Place> myPlaces;
   private Place myCurrentPlace;

   public World(GUIManager guiManager, Rectangle bounds) {
      myGUIManager = guiManager;
      myBounds = bounds;
      myPlayer = new Player("", null, Color.red);
      myMouseGoto = new MouseGoto(null);
      myPlaces = new HashMap<String, Place>();
   }

   @Override
   public void draw(Graphics pen) {
      if ( myCurrentPlace != null ) {
         myCurrentPlace.draw(pen);
      }
   }
   @Override
   public void update() {
      if ( myCurrentPlace != null ) {
         myCurrentPlace.update();
      }
   }

   public void keyPressed(KeyEvent event) {}
   public void keyReleased(KeyEvent event) {}
   public void keyTyped(KeyEvent event) {}

   public void mouseClicked(MouseEvent event) {}
   public void mouseDragged(MouseEvent event) {}
   public void mouseEntered(MouseEvent event) {}
   public void mouseExited(MouseEvent event) {}
   public void mouseMoved(MouseEvent event) {}
   public void mousePressed(MouseEvent event) {}
   public void mouseReleased(MouseEvent event) {}
   public void mouseWheelMoved(MouseWheelEvent event) {}

   @Override
   public void addComponent(Place toAdd) {
      // error if area name taken
      if ( myPlaces.keySet().contains(toAdd.getName()) ) {
         String message = "Area " + toAdd.getName() + " already exists.";
         throw new IllegalArgumentException(message);
      }

      myPlaces.put(toAdd.getName(), toAdd);
   }

   @Override
   public void removeComponent(Place toRemove) {
      myPlaces.remove(toRemove.getName());
   }

   public GUIManager getGUIManager() { return myGUIManager; }
   public Player getPlayer() { return myPlayer; }
   public MouseGoto getMouseGoto() { return myMouseGoto; }
   @Override
   public Collection<Place> getComponents() { return myPlaces.values(); }
   public Rectangle getBounds() { return myBounds; }
   public void setBounds(Rectangle bounds) { myBounds = bounds; }
   public Place getPlace(String name) { return myPlaces.get(name); }

   public void setPlace(Place toSet) {
      if ( !myPlaces.containsValue(toSet) ) {
         addComponent(toSet);
      }
      myCurrentPlace = toSet;
   }
}
