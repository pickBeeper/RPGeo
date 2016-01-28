package rpgeo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import gfm.gui.GUIBox;

public class ChatBox extends GUIBox {
   private Player myPlayer;
   private LinkedList<ChatMessage> myMessages;
   private String myCurrentMessage;

   public ChatBox(Rectangle box, Color color, Player player) {
      super(box, color, 0, 30, 3);
      myPlayer = player;
      myMessages = new LinkedList<ChatMessage>();
      myCurrentMessage = "";
   }

   @Override
   public void keyPressed(KeyEvent event) {
      int code = event.getKeyCode();
      checkToActivate(code);
      checkToSend(code);
      checkToAppend(code);
   }

   public void checkToActivate(int keyCode) {
      if ( keyCode == KeyEvent.VK_SLASH && !isActivated() ) {
         activate();
      }
   }

   public void checkToSend(int keyCode) {
      if ( isActivated() && keyCode == KeyEvent.VK_ENTER ) {
         // ensure message isn't blank
         boolean validMessage = false;
         for ( int i = 0; i < myCurrentMessage.length(); i++ ) {
            if ( myCurrentMessage.charAt(i) != ' ' ) {
               validMessage = true;
               break;
            }
         }

         // send message if valid
         if ( validMessage ) {
            myMessages.add(new ChatMessage(myPlayer, myCurrentMessage));
         }

         // reset message
         myCurrentMessage = "";
         deactivate();
      }
   }

   public void checkToAppend(int keyCode) {
      String entered = KeyEvent.getKeyText(keyCode);
      if ( isActivated() && entered.length() == 1 ) {
         myCurrentMessage += entered;
      }
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