package rpgeo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.gamestate.GameState;

public class Test extends GameState {
   private Rectangle myPlayBounds;
   private MouseGoto myMouseGoto;

   public Test(Game game) {
      super(game);
   }

   public Test(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void draw(Graphics pen) {
      myMouseGoto.draw(pen);
   }

   @Override
   public void update() {
      myMouseGoto.update();
   }

   @Override
   public void initUI() {
      // TODO Auto-generated method stub

   }

   @Override
   public void init() {
      myPlayBounds = new Rectangle();
      // based on numbers in GameGUI
      myPlayBounds.setLocation(getWidth() / 6 + 8, 4);
      myPlayBounds.setSize(getWidth() * 5 / 6 - 8, getHeight() * 4 / 5);
      myMouseGoto = new MouseGoto(myPlayBounds);
   }

   @Override
   public void keyPressed(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyReleased(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void keyTyped(KeyEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent event) {
      myMouseGoto.mouseClicked(event);
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      myMouseGoto.mouseDragged(event);
   }

   @Override
   public void mouseEntered(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseMoved(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mousePressed(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      // TODO Auto-generated method stub

   }

}
