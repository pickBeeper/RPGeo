package rpgeo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class ChatBox extends GUIBox {
   private Player myPlayer;
   private LinkedList<ChatMessage> myMessages;
   private String myCurrentMessage;

   public ChatBox(Rectangle box, Color color, Player player) {
      super(box, color);
      myPlayer = player;
      myMessages = new LinkedList<ChatMessage>();
      myCurrentMessage = "";
   }

   public void keyPressed(KeyEvent event) {
      int code = event.getKeyCode();

      if ( code == KeyEvent.VK_SLASH && !getIsActivated() ) {
         activate();
      } else if ( getIsActivated() ) {
         if ( code == KeyEvent.VK_ENTER ) {
            myMessages.add(new ChatMessage(myPlayer, myCurrentMessage));
            myCurrentMessage = "";
            deactivate();
         } else if ( getIsActivated() && KeyEvent.getKeyText(code).length() == 1 ) {
            myCurrentMessage += event.getKeyChar();
         }
      }
   }

   public void keyReleased(KeyEvent event) {
   }
   public void keyTyped(KeyEvent event) {
   }
}

class ChatMessage {
   private String mySender;
   private String myMessage;

   public ChatMessage(Player player, String message) {
      mySender = player.getName();
      myMessage = message;
   }
}