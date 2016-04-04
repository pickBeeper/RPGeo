package rpgeo.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import rpgeo.Grid;
import rpgeo.World;

public class Play extends GameState {
   private World myWorld;
   private GameGUI myGameGUI;

   public Play(Game game) {
      super(game);
   }

   public Play(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void draw(Graphics pen) {
      myWorld.draw(pen);
      getGUIManager().draw(pen);
   }

   @Override
   public void update() {
      myWorld.update();
      getGUIManager().update();
   }

   @Override
   public void initGUI() {
      myGameGUI.initGUI();
   }

   @Override
   public void init() {
      // bounds to draw world
      Rectangle bounds = new Rectangle();
      bounds.setLocation(4, 4);
      bounds.setSize(getWidth()  - 8, getHeight() * 4 / 5);

      myWorld = new World(getGUIManager(), bounds);

      FileInputStream fileIn = null;
      ObjectInputStream in = null;
      Grid grid = null;
      try {
         String filename = JOptionPane.showInputDialog("To Open: ");
         if ( filename == null || filename == "" ) {
            return;
         }
         fileIn = new FileInputStream(new File(filename));
         in = new ObjectInputStream(fileIn);
         grid = (Grid) in.readObject();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } finally {
         try {
            if ( in != null ) {
               in.close();
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      myWorld.getPlayer().setTile(grid.getTile(0, 0));
      grid.getTile(0, 0).addComponent(myWorld.getPlayer());
      myWorld.setGrid(grid);

      myGameGUI = new GameGUI(this);
   }

   @Override
   public void keyPressed(KeyEvent event) {
      getGUIManager().keyPressed(event);
      myWorld.keyPressed(event);
   }

   @Override
   public void keyReleased(KeyEvent event) {
      getGUIManager().keyReleased(event);
      myWorld.keyReleased(event);
   }

   @Override
   public void keyTyped(KeyEvent event) {
      getGUIManager().keyTyped(event);
      myWorld.keyTyped(event);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
      getGUIManager().mouseClicked(event);
      myWorld.mouseClicked(event);
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      getGUIManager().mouseDragged(event);
      myWorld.mouseDragged(event);
   }

   @Override
   public void mouseEntered(MouseEvent event) {
      getGUIManager().mouseEntered(event);
      myWorld.mouseEntered(event);
   }

   @Override
   public void mouseExited(MouseEvent event) {
      getGUIManager().mouseExited(event);
      myWorld.mouseExited(event);
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      getGUIManager().mouseMoved(event);
      myWorld.mouseMoved(event);
   }

   @Override
   public void mousePressed(MouseEvent event) {
      getGUIManager().mousePressed(event);
      myWorld.mousePressed(event);
   }

   @Override
   public void mouseReleased(MouseEvent event) {
      getGUIManager().mouseReleased(event);
      myWorld.mouseReleased(event);
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      getGUIManager().mouseWheelMoved(event);
      myWorld.mouseWheelMoved(event);
   }

   protected World getWorld() { return myWorld; }
}