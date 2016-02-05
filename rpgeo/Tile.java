package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import rpgeo.game.BasicTickable;

public class Tile extends BasicTickable {
   private static final String[] myPermAttributes = new String[] {
         "background",
         "passable"
   };

   private Grid myGrid;
   private Rectangle myRect;
   private int myRow;
   private int myCol;
   private HashMap<String, Object> myAttributes;
   private LinkedList<Tickable> myChildren;

   public Tile(Grid grid, Rectangle rect, int row, int col) {
      this(grid, rect, Color.black, true, row, col);
   }

   public Tile(Grid grid, Rectangle rect, Color bg, Boolean passable, int row, int col) {
      myGrid = grid;
      myRect = rect;
      myAttributes = new HashMap<String, Object>();
      myAttributes.put("background", bg);
      myAttributes.put("passable", passable);
      myRow = row;
      myCol = col;
      myChildren = new LinkedList<Tickable>();
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor((Color) myAttributes.get("background"));
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

   @Override
   public void tick() {
      for ( Tickable toTick : myChildren ) {
         toTick.tick();
      }
   }

   @Override
   public void addComponent(Tickable toAdd) {
      myChildren.add(toAdd);
   }
   @Override
   public void removeComponent(Tickable toRemove) {
      myChildren.remove(toRemove);
   }

   public void removeAttribute(String name, Object obj) {
      for ( String perm : myPermAttributes ) {
         if ( perm.equals(name) ) {
            String mssg = "Can't remove attribute " + name + ".";
            throw new IllegalArgumentException(mssg);
         }
      }
   }

   @Override
   public Collection<Tickable> getComponents() { return myChildren; }
   public Grid getGrid() { return myGrid; }
   public int getRow() { return myRow; }
   public int getCol() { return myCol; }
   public Rectangle getRect() { return myRect; }
   public void setRect(Rectangle rect) { myRect = rect; }
   public Object getAttribute(String name) { return myAttributes.get(name); }
   public void setAttribute(String name, Object obj) { myAttributes.put(name, obj); }
   public Color getBackground() { return (Color) myAttributes.get("background"); }
   public void setBackground(Color color) { myAttributes.put("background", color); }
}