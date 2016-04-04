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
import rpgeo.game.MouseGoto;
import rpgeo.game.Player;

public class World implements GameComponent<Grid> {
   private GUIManager myGUIManager;
   private Rectangle myBounds;
   private Player myPlayer;
   private MouseGoto myMouseGoto;
   private HashMap<String, Grid> myGrids;
   private Grid myCurrentGrid;
   private int myTime;

   public World(GUIManager guiManager, Rectangle bounds) {
      myGUIManager = guiManager;
      myBounds = bounds;
      myPlayer = new Player("", null, Color.red);
      myMouseGoto = new MouseGoto(myBounds, myPlayer);
      myGrids = new HashMap<String, Grid>();
      myTime = 0;
   }

   @Override
   public void draw(Graphics pen) {
      if ( myCurrentGrid != null ) {
         myCurrentGrid.draw(pen);
      }

      myMouseGoto.draw(pen);
   }
   @Override
   public void update() {
      if ( myCurrentGrid != null ) {
         myCurrentGrid.update();
         if ( myTime % 5 == 0 ) {
            myCurrentGrid.tick();
         }
      }

      myMouseGoto.update();

      myTime++;
   }

   public void keyPressed(KeyEvent event) {}
   public void keyReleased(KeyEvent event) {}
   public void keyTyped(KeyEvent event) {}

   public void mouseClicked(MouseEvent event) {
      myMouseGoto.mouseClicked(event);
   }
   public void mouseDragged(MouseEvent event) {
      myMouseGoto.mouseDragged(event);
   }
   public void mouseEntered(MouseEvent event) {}
   public void mouseExited(MouseEvent event) {}
   public void mouseMoved(MouseEvent event) {
      myMouseGoto.mouseMoved(event);
   }
   public void mousePressed(MouseEvent event) {}
   public void mouseReleased(MouseEvent event) {}
   public void mouseWheelMoved(MouseWheelEvent event) {}

   @Override
   public void addComponent(Grid toSet) {
      // error if area name taken
      if ( myGrids.keySet().contains(toSet.getName()) ) {
         String mssg = "Area " + toSet.getName() + " already exists.";
         throw new IllegalArgumentException(mssg);
      }

      myGrids.put(toSet.getName(), toSet);
   }

   @Override
   public void removeComponent(Grid toRemove) {
      myGrids.remove(toRemove.getName());
   }

   public GUIManager getGUIManager() { return myGUIManager; }
   public Player getPlayer() { return myPlayer; }
   public MouseGoto getMouseGoto() { return myMouseGoto; }
   @Override
   public Collection<Grid> getComponents() { return myGrids.values(); }
   public Rectangle getBounds() { return myBounds; }
   public void setBounds(Rectangle bounds) { myBounds = bounds; }
   public Grid getGrid(String name) { return myGrids.get(name); }

   public void setGrid(Grid toSet) {
      if ( !myGrids.containsValue(toSet) ) {
         addComponent(toSet);
      }
      myCurrentGrid = toSet;
      myMouseGoto.setGrid(myCurrentGrid);
   }
}
