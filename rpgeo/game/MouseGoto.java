package rpgeo.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import rpgeo.Grid;
import rpgeo.Tile;

/**
 * The Class MouseGoto.
 */
public class MouseGoto {

   /** The bounds for the mouse goto. */
   private Rectangle myBounds;

   /** The player to direct. */
   private Mob myPlayer;

   /** Whether the mouse goto is activated */
   private boolean myIsActivated;

   /** The place of the chosen tile */
   private Grid myGrid;

   /** The chosen tile */
   private Tile myTile;

   private int myFollow;

   /**
    * Instantiates a new mouse goto.
    *
    * @param bounds the bounds
    */
   public MouseGoto(Rectangle bounds, Mob player) {
      myBounds = bounds;
      myPlayer = player;
      myIsActivated = false;
      myFollow = 0;
   }

   /**
    * Draw the mouse goto's animation
    *
    * @param pen the Graphics object to draw with
    */
   public void draw(Graphics pen) {
      if ( myIsActivated ) {
         Rectangle bounds = myTile.getRect();
         int x = (int) bounds.getX();
         int y = (int) bounds.getY();
         int width = (int) bounds.getWidth();
         int height = (int) bounds.getHeight();
         pen.setColor(Color.black);
         pen.drawRect(x, y, width, height);
      }
   }

   /**
    * Update the mouse goto and animation.
    */
   public void update() {
      if ( myIsActivated ) {
         Tile pTile = myPlayer.getTile();
         if ( myTile == pTile ) {
            myIsActivated = false;
            return;
         }

         // calculate tile dist
         int xDist = myTile.getCol() - pTile.getCol();
         int yDist = myTile.getRow() - pTile.getRow();
         // calcuate angle
         double angle = Math.atan2(yDist, xDist);
         // get if x/y components -1, 0, or 1
         int xMove = (int) Math.round(Math.cos(angle));
         int yMove = (int) Math.round(Math.sin(angle));
         myPlayer.move(xMove, yMove);
      }
   }

   /**
    * Mouse clicked.
    *
    * @param event the event
    */
   public void mouseClicked(MouseEvent event) {
      Point point = event.getPoint();

      if ( !myBounds.contains(point) ) {
         return;
      }

      Tile[][] tiles = myGrid.getTiles();
      for ( int r = 0; r < tiles.length; r++ ) {
         for ( int c = 0; c < tiles[0].length; c++ ) {
            if ( tiles[ r ][ c ].getRect().contains(point) ) {
               myTile = tiles[ r ][ c ];
               myIsActivated = true;
               return;
            }
         }
      }
   }

   /**
    * Mouse dragged.
    *
    * @param event the event
    */
   public void mouseDragged(MouseEvent event) {
      Point point = event.getPoint();

      Tile[][] tiles = myGrid.getTiles();
      for ( int r = 0; r < tiles.length; r++ ) {
         for ( int c = 0; c < tiles[0].length; c++ ) {
            if ( tiles[ r ][ c ].getRect().contains(point) ) {
               myTile = tiles[ r ][ c ];
               myIsActivated = true;
               return;
            }
         }
      }
   }

   public void mouseMoved(MouseEvent event) {
      // System.out.println(myFollow);
      if ( myFollow % 4 >= 2 ) {
         Point point = event.getPoint();

         Tile[][] tiles = myGrid.getTiles();
         for ( int r = 0; r < tiles.length; r++ ) {
            for ( int c = 0; c < tiles[0].length; c++ ) {
               if ( tiles[ r ][ c ].getRect().contains(point) ) {
                  myTile = tiles[ r ][ c ];
                  myIsActivated = true;
                  return;
               }
            }
         }
      }
   }

   public void toggleFollow() {
      myFollow += 1;
   }


   /**
    * Activate the mouse goto.
    */
   public void activate() { myIsActivated = true; }

   /**
    * Deactivate the mouse goto.
    */
   public void deactivate() { myIsActivated = false; }

   /**
    * Sets the place.
    *
    * @param place the new place
    */
   public void setGrid(Grid place) { myGrid = place; }
}