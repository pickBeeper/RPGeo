package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import gfm.GameComponent;
import gfm.util.ColorCross;

public class Place implements GameComponent<DrawUpdatable> {
   private World myWorld;
   private int myWidth;
   private int myHeight;
   private String myName;
   private Rectangle myBounds;
   private LinkedList<DrawUpdatable> myComponents;
   private Grid myGrid;

   public Place(World world, int width, int height, String name) {
      myWorld = world;
      myWidth = width;
      myHeight = height;
      myName = name;
      myBounds = myWorld.getBounds();
      myComponents = new LinkedList<DrawUpdatable>();
      myGrid = new Grid(myBounds, myWidth, myHeight);
   }

   @Override
   public void draw(Graphics pen) {
      myGrid.draw(pen);

      for ( DrawUpdatable toDraw : myComponents ) {
         toDraw.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( DrawUpdatable toUpdate : myComponents ) {
         toUpdate.update();
      }
   }

   @Override
   public void addComponent(DrawUpdatable toAdd) {
      myComponents.add(toAdd);
   }

   @Override
   public void removeComponent(DrawUpdatable toRemove) {
      myComponents.remove(toRemove);
   }

   @Override
   public Collection<DrawUpdatable> getComponents() {
      return myComponents;
   }

   public World getWorld() { return myWorld; }
   public String getName() { return myName; }
}

class Grid {
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
            myTiles[ r ][ c ] = new Tile(bounds, color);
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
}

// updates player

class Tile implements GameComponent<DrawUpdatable> {
   private Rectangle myRect;
   private Color myBackground;
   private LinkedList<DrawUpdatable> myChildren;

   public Tile(Rectangle rect, Color background) {
      myRect = rect;
      myBackground = background;
      myChildren = new LinkedList<DrawUpdatable>();
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(myBackground);
      pen.fillRect(myRect.x, myRect.y, myRect.width, myRect.height);

      for ( DrawUpdatable child : myChildren ) {
         child.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( DrawUpdatable child : myChildren ) {
         child.update();
      }
   }

   public Rectangle getRect() { return myRect; }
   public void setRect(Rectangle rect) { myRect = rect; }


   @Override
   public void addComponent(DrawUpdatable toAdd) {
      myChildren.add(toAdd);
   }

   @Override
   public void removeComponent(DrawUpdatable toRemove) {
      myChildren.remove(toRemove);
   }

   @Override
   public Collection<DrawUpdatable> getComponents() {
      // TODO Auto-generated method stub
      return null;
   }
}