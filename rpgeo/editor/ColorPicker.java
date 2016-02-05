package rpgeo.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gfm.gui.GUIComponentAdapter;

class ColorPicker extends GUIComponentAdapter {
   private static int hitboxHeight = 8;

   private int myX;
   private int myY;
   private int myWidth;
   private int myHeight;
   private Color myColor;
   private Point myRedPoint;
   private Point myGreenPoint;
   private Point myBluePoint;
   private Point myCurrPoint;

   public ColorPicker(int x, int y, int width, int height) {
      myX = x;
      myY = y;
      myWidth = width;
      myHeight = height;
      myColor = new Color(12, 144, 56);
      myRedPoint = new Point(myX, myY + myHeight / 4);
      myGreenPoint = new Point(myX, myY + myHeight *  1 / 2);
      myBluePoint = new Point(myX, myY + myHeight * 3 / 4);
      myCurrPoint = null;
   }

   @Override
   public void draw(Graphics pen) {
      int x0 = myX;
      int x1 = myX + myWidth - myHeight;

      int handleSize = hitboxHeight;
      int halfHandleSize = hitboxHeight / 2;

      pen.setColor(Color.red);
      int y = myY + myHeight / 4;
      pen.drawLine(x0, y, x1, y);
      int marker = (int) myRedPoint.getX();
      pen.fillRect(marker - halfHandleSize,
            y - halfHandleSize, handleSize, handleSize);

      pen.setColor(Color.green);
      y = myY + myHeight * 1 / 2;
      pen.drawLine(x0, y, x1, y);
      marker = (int) myGreenPoint.getX();
      pen.fillRect(marker - halfHandleSize,
            y - halfHandleSize, handleSize, handleSize);

      pen.setColor(new Color(50, 50, 255));
      y = myY + myHeight * 3 / 4;
      pen.drawLine(x0, y, x1, y);
      marker = (int) myBluePoint.getX();
      pen.fillRect(marker - halfHandleSize,
            y - halfHandleSize, handleSize, handleSize);

      pen.setColor(myColor);
      y = myY + myHeight / 4;
      pen.fillRect(x1 + 27, y, myHeight / 2, myHeight / 2);
   }

   @Override
   public void mousePressed(MouseEvent event) {
      int width = myWidth - myHeight;

      // make hitboxes for dragging
      int redHeight = (int) myRedPoint.getY() - hitboxHeight / 2;
      Rectangle red = new Rectangle(myX, redHeight, width, hitboxHeight);
      int greenHeight = (int) myGreenPoint.getY() - hitboxHeight / 2;
      Rectangle green = new Rectangle(myX, greenHeight, width, hitboxHeight);
      int blueHeight = (int) myBluePoint.getY() - hitboxHeight / 2;
      Rectangle blue = new Rectangle(myX, blueHeight, width, hitboxHeight);

      boolean colorChange = true;
      if ( red.contains(event.getPoint()) ) {
         int heightRed = (int) myRedPoint.getY();
         myRedPoint.setLocation(event.getX(), heightRed);
         myCurrPoint = myRedPoint;
      } else if ( green.contains(event.getPoint()) ) {
         int heightGreen = (int) myGreenPoint.getY();
         myGreenPoint.setLocation(event.getX(), heightGreen);
         myCurrPoint = myGreenPoint;
      } else if ( blue.contains(event.getPoint()) ) {
         int heightBlue = (int) myBluePoint.getY();
         myBluePoint.setLocation(event.getX(), heightBlue);
         myCurrPoint = myBluePoint;
      } else {
         colorChange = false;
         myCurrPoint = null;
      }

      if ( colorChange ) {
         updateColor();
      }
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      if ( myCurrPoint != null ) {
         double y = myCurrPoint.getY();

         double x  = event.getX();
         if ( x > myX + myWidth - myHeight ) {
            x = myX + myWidth - myHeight;
         } else if ( x < myX ) {
            x = myX;
         }

         myCurrPoint.setLocation(x, y);
         updateColor();
      }
   }

   public void updateColor() {
      int red = (int) (255 * (myRedPoint.getX() - myX) / (myWidth - myHeight));
      int green = (int) (255 * (myGreenPoint.getX() - myX) / (myWidth - myHeight));
      int blue = (int) (255 * (myBluePoint.getX() - myX) / (myWidth - myHeight));

      myColor = new Color(red, green, blue);
   }

   public Color getColor() { return myColor; }
   public void setColor(Color color) {
      myColor = color;

      myRedPoint.x = (int) (myColor.getRed() * (myWidth - myHeight) / 255.0 + myX);
      myGreenPoint.x = (int) (myColor.getGreen() * (myWidth - myHeight) / 255.0 + myX);
      myBluePoint.x = (int) (myColor.getBlue() * (myWidth - myHeight) / 255.0 + myX);
   }
}