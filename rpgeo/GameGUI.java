package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.Macro;

public class GameGUI implements Macro {
   private Game myGame;
   private Player myPlayer;
   private ChatBox myChatBox;
   private GUIBox myHubBox;

   public GameGUI(Game game, Player player) {
      myGame = game;
      myPlayer = player;
      init();
   }

   public void init() {
      Color color = new Color(255, 255, 255, 70);

      Rectangle chatBounds = new Rectangle();
      chatBounds.setLocation(4,  4);
      chatBounds.setSize(myGame.getWidth() / 6, myGame.getHeight() * 4 / 5);
      myChatBox = new ChatBox(chatBounds, color, myPlayer);

      Rectangle hubBounds = new Rectangle();
      hubBounds.setLocation(4, 8 + myGame.getHeight() * 4 / 5);
      hubBounds.setSize(myGame.getWidth() - 8, myGame.getHeight() / 5 - 12);
      myHubBox = new GUIBox(hubBounds, color);
   }

   @Override
   public void draw(Graphics pen) {
      myChatBox.draw(pen);
      myHubBox.draw(pen);
   }

   @Override
   public void update() {
      myChatBox.update();
      myHubBox.update();
   }

   @Override
   public void keyPressed(KeyEvent event) {
      myChatBox.keyPressed(event);
   }

   @Override
   public void keyReleased(KeyEvent event) {
      myChatBox.keyReleased(event);
   }

   @Override
   public void keyTyped(KeyEvent event) {
      myChatBox.keyTyped(event);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseDragged(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent event) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent event) {
      myHubBox.deactivate();
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      myHubBox.setActivated(myHubBox.contains(event));   }

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