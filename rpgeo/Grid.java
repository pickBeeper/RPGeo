package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gfm.util.ColorCross;

public class Grid {
   public enum DIR {
      LEFT, RIGHT, UP, DOWN, UPLEFT,
      UPRIGHT, DOWNLEFT, DOWNRIGHT
   }

   private Rectangle myBounds;
   private int myColumns;
   private int myRows;
   public Tile[][] myTiles;

   public Grid(Rectangle bounds, int cols, int rows) {
      myBounds = bounds;
      myColumns = cols;
      myRows = rows;
      setUpGrid();
   }

   public void setUpGrid() {
      int xOff = myBounds.x;
      int yOff = myBounds.y;
      int allWidth = myBounds.width;
      int allHeight = myBounds.height;

      // int width = allWidth / myColumns;
      // int height = allHeight / myRows;
      // int

      myTiles = new Tile[ myRows ] [ myColumns ];
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            // get start off current tile
            int x = (int) (c / (double) myColumns * allWidth) + xOff;
            int y = (int) (r / (double) myRows * allHeight) + yOff;

            // use start of next tile for size
            int width = (int) ((c + 1) / (double) myColumns * allWidth) + xOff - x;
            int height = (int) ((r + 1) / (double) myRows * allHeight) + yOff - y;

            Rectangle bounds = new Rectangle(x, y, width, height);
            Color color = ColorCross.randColor();
            myTiles[ r ][ c ] = new Tile(this, bounds, color);
         }
      }
   }

   public void draw(Graphics pen) {
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            myTiles[ r ][ c ].draw(pen);
         }
      }
   }

   public void update() {
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            myTiles[ r ][ c ].update();
         }
      }
   }

   public Tile getTile(int row, int col) { return myTiles[ row ][ col ]; }
}