package rpgeo.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Macro;
import gfm.gui.GUIBox;
import gfm.gui.GUIManager;

public class GameGUI implements Macro {
   private Play myPlay;
   private GUIManager myGUIManager;
   private Player myPlayer;
   private ChatBox myChatBox;
   private GameHubBox myHubBox;

   public GameGUI(Play play) {
      myPlay = play;
      myGUIManager = play.getGUIManager();
      myPlayer = myPlay.getWorld().getPlayer();
      initGUI();
   }

   public void initGUI() {
      // get some vars
      int width = myPlay.getWidth();
      int height = myPlay.getHeight();
      Color color = new Color(200, 200, 200, 70);

      // set bounds and instantiate
      Rectangle chatBounds = new Rectangle();
      chatBounds.setLocation(4,  4);
      chatBounds.setSize(width / 6, height * 4 / 5);
      myChatBox = new ChatBox(chatBounds, color, myPlayer);

      // set bounds and instantiate
      Rectangle hubBounds = new Rectangle();
      hubBounds.setLocation(4, 8 + height * 4 / 5);
      hubBounds.setSize(width - 8, height / 5 - 12);
      int alpha = color.getAlpha();
      myHubBox = new GameHubBox(this, hubBounds, color, alpha / 2, alpha, 4);

      // add components
      myGUIManager.addGUIComponent(myChatBox);
      myGUIManager.addGUIComponent(myHubBox);
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
   public void keyPressed(KeyEvent event) { }
   @Override
   public void keyReleased(KeyEvent event) { }
   @Override
   public void keyTyped(KeyEvent event) { }
   @Override
   public void mouseClicked(MouseEvent event) { }
   @Override
   public void mouseDragged(MouseEvent event) { }
   @Override
   public void mouseEntered(MouseEvent event) { }
   @Override
   public void mouseExited(MouseEvent event) {
      myHubBox.deactivate();
   }
   @Override
   public void mouseMoved(MouseEvent event) {
      myHubBox.setActivated(myHubBox.contains(event));
   }
   @Override
   public void mousePressed(MouseEvent event) { }
   @Override
   public void mouseReleased(MouseEvent event) { }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) { }

   public Play getPlay() { return myPlay; }
   public ChatBox getChatBox() { return myChatBox; }
}

class GameHubBox extends GUIBox {
   private GameGUI myGameGUI;

   public GameHubBox(GameGUI gui, Rectangle box, Color color, int minOpacity, int maxOpacity, int opacitySpeed) {
      super(box, color, minOpacity, maxOpacity, opacitySpeed);
      myGameGUI = gui;
   }

   @Override
   public void keyPressed(KeyEvent event) {
      if ( !myGameGUI.getChatBox().isActivated() &&
            KeyEvent.getKeyText(event.getKeyCode()).length() == 1) {
         doCommand(event.getKeyChar());
      }
   }

   public synchronized void doCommand(char c) {
      if ( c == 'f' ) {
         myGameGUI.getPlay().getWorld().getMouseGoto().toggleFollow();
      }
   }

   public GameGUI getGameGUI() { return myGameGUI; }
}