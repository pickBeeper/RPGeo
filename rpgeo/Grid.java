package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import gfm.util.ColorCross;
import rpgeo.game.BasicTickable;

public class Grid extends BasicTickable implements Serializable {
   private static final long serialVersionUID = 3177855217308926562L;

   private String myName;
   private Rectangle myBounds;
   private int myColumns;
   private int myRows;
   private Tile[][] myTiles;
   private LinkedList<Tickable> myComponents;

   public Grid(String name, Rectangle bounds, int cols, int rows) {
      myName = name;
      myBounds = bounds;
      myColumns = cols;
      myRows = rows;
      setUpGrid();
      myComponents = new LinkedList<Tickable>();
   }

   public void setUpGrid() {
      int xOff = myBounds.x;
      int yOff = myBounds.y;
      int allWidth = myBounds.width;
      int allHeight = myBounds.height;

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
            myTiles[ r ][ c ] = new Tile(this, bounds, color, true, r, c);
         }
      }
   }

   @Override
   public void tick() {
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            myTiles[ r ][ c ].tick();
         }
      }
   }

   @Override
   public void draw(Graphics pen) {
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            myTiles[ r ][ c ].draw(pen);
         }
      }
   }

   @Override
   public void update() {
      for ( int r = 0; r < myRows; r++ ) {
         for ( int c = 0; c < myColumns; c++ ) {
            myTiles[ r ][ c ].update();
         }
      }
   }

   public boolean inBounds(int row, int col) {
      if ( col >= myTiles[0].length || col < 0 ) {
         return false;
      } else if ( row >= myTiles.length || row < 0 ) {
         return false;
      }
      return true;
   }

   @Override
   public void addComponent(Tickable toAdd) {
      myComponents.add(toAdd);
   }
   @Override
   public void removeComponent(Tickable toRemove) {
      myComponents.remove(toRemove);

   }

   public Tile[][] getTiles() { return myTiles; }
   public Tile getTile(int row, int col) { return myTiles[ row ][ col ]; }
   public String getName() { return myName; }
   public void setName(String name) { myName = name; }
   public Rectangle getBounds() { return myBounds; }
   @Override
   public Collection<Tickable> getComponents() { return myComponents; }
}