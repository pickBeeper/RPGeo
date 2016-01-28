package rpgeo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.gamestate.GameState;

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
      myWorld.setPlace(new Place(myWorld, 30, 25, "test"));

      myGameGUI = new GameGUI(this);
   }

   @Override
   public void keyPressed(KeyEvent event) {
      getGUIManager().keyPressed(event);
   }

   @Override
   public void keyReleased(KeyEvent event) {
      getGUIManager().keyReleased(event);
   }

   @Override
   public void keyTyped(KeyEvent event) {
      getGUIManager().keyTyped(event);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
      getGUIManager().mouseClicked(event);
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      getGUIManager().mouseDragged(event);
   }

   @Override
   public void mouseEntered(MouseEvent event) {
      getGUIManager().mouseEntered(event);
   }

   @Override
   public void mouseExited(MouseEvent event) {
      getGUIManager().mouseExited(event);
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      getGUIManager().mouseMoved(event);
   }

   @Override
   public void mousePressed(MouseEvent event) {
      getGUIManager().mousePressed(event);
   }

   @Override
   public void mouseReleased(MouseEvent event) {
      getGUIManager().mouseReleased(event);
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      getGUIManager().mouseWheelMoved(event);
   }

   protected World getWorld() { return myWorld; }
}