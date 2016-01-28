package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gfm.util.Vec2;

// TODO: Auto-generated Javadoc
/**
 * The Class MouseGoto.
 */
public class MouseGoto {
   
   /** The my bounds. */
   private Rectangle myBounds;
   
   /** The my is activated. */
   private boolean myIsActivated;
   
   /** The my pos. */
   private Vec2 myPos;
   
   /** The my animation. */
   private MouseGotoAnimation myAnimation;

   /**
    * Instantiates a new mouse goto.
    *
    * @param bounds the bounds
    */
   public MouseGoto(Rectangle bounds) {
      myBounds = bounds;
      myIsActivated = false;
      myPos = new Vec2();
      myAnimation = new MouseGotoAnimation(Color.red);
   }

   /**
    * Draw.
    *
    * @param pen the pen
    */
   public void draw(Graphics pen) {
      if ( myIsActivated ) {
         myAnimation.draw(pen);
      }
   }

   /**
    * Update.
    */
   public void update() {
      if ( myIsActivated ) {
         myAnimation.update();
      }
   }

   /**
    * Mouse clicked.
    *
    * @param event the event
    */
   public void mouseClicked(MouseEvent event) {
      if ( myBounds.contains(event.getPoint()) ) {
         activate();
         myPos.setX(event.getX());
         myPos.setY(event.getY());
         myAnimation.reset();
         myAnimation.setPos(myPos.copy());
      }
   }

   /**
    * Mouse dragged.
    *
    * @param event the event
    */
   public void mouseDragged(MouseEvent event) {
      if ( myBounds.contains(event.getPoint()) ) {
         activate();
         myPos.setX(event.getX());
         myPos.setY(event.getY());
         myAnimation.setPos(myPos.copy());
      }
   }

   /**
    * Activate.
    */
   public void activate() { myIsActivated = true; }
   
   /**
    * Deactivate.
    */
   public void deactivate() { myIsActivated = false; }
}

class MouseGotoAnimation {
   private Color myColor;
   private int myFrame;
   private Vec2 myPos;
   private Vec2[] myPoints;

   public MouseGotoAnimation(Color color) {
      myColor = color;
      myFrame = 0;
      myPos = new Vec2();
   }

   public void draw(Graphics pen) {
      pen.setColor(myColor);

      rotateAndScale();
      int[] xCoords = new int[] {
            (int) myPoints[0].getX(), (int) myPoints[1].getX(),
            (int) myPoints[2].getX(), (int) myPoints[3].getX() };
      int[] yCoords = new int[] {
            (int) myPoints[0].getY(), (int) myPoints[1].getY(),
            (int) myPoints[2].getY(), (int) myPoints[3].getY() };

      Polygon square = new Polygon(xCoords, yCoords, 4);
      pen.fillPolygon(square);
   }

   public void rotateAndScale() {
      myPoints = new Vec2[] {
            new Vec2(-1, -1), new Vec2(1, -1),
            new Vec2(1, 1), new Vec2(-1, 1) };

      for ( Vec2 point : myPoints ) {
         point.setMagnitude(7);
         point.rotateDegrees(5 * myFrame);
         point.addVector(myPos);
      }
   }

   public void update() {
      myFrame++;
   }

   public void reset() {
      myFrame = 0;
   }

   public void setPos(Vec2 pos) { myPos = pos; }
}
