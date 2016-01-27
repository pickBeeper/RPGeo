package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gfm.util.ColorCross;

class GUIBox {
   private static int opacitySpeed = 4;

   private Rectangle myBox;
   private Color myColor;
   private int myMaxOpacity;
   private int myMinOpacity;
   private int myCurrentOpacity;
   private int myOpacityDirection;
   private boolean myIsActivated;

   public GUIBox(Rectangle box, Color color) {
      myBox = box;
      myColor = color;
      myMaxOpacity = myColor.getAlpha();
      myMinOpacity = myMaxOpacity / 6 * 4;
      myCurrentOpacity = myMinOpacity;
      myOpacityDirection = -opacitySpeed;
      myIsActivated = false;
   }

   public void draw(Graphics pen) {
      Color fill = ColorCross.alpha(myColor, myCurrentOpacity);
      pen.setColor(fill);
      pen.fillRect(myBox.x, myBox.y, myBox.width, myBox.height);
   }

   public void update() {
      if ( myOpacityDirection == -opacitySpeed ) {
         if ( myCurrentOpacity > myMinOpacity ) {
            myCurrentOpacity -= opacitySpeed;
         } else if ( myIsActivated ) {
            myOpacityDirection = opacitySpeed;
         }
      } else if ( myOpacityDirection == opacitySpeed ) {
         if ( myCurrentOpacity < myMaxOpacity ) {
            myCurrentOpacity += opacitySpeed;
         } else if ( !myIsActivated ) {
            myOpacityDirection = -opacitySpeed;
         }
      }
   }

   public boolean contains(MouseEvent event) {
      if ( myBox.contains(event.getPoint()) ) {
         return true;
      }
      return false;
   }

   public void activate() { myIsActivated = true; }
   public void deactivate() { myIsActivated = false; }
   public void setActivated(boolean activated) { myIsActivated = activated; }
   public boolean getIsActivated() { return myIsActivated; }

   public Rectangle getBox() { return myBox; }
}