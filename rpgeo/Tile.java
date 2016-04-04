package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import rpgeo.game.BasicTickable;

public class Tile extends BasicTickable implements Serializable {
   /**
    *
    */
   private static final long serialVersionUID = 6762308716837517579L;

   private static final String[] myPermAttributes = new String[] {
         "color",
         "passable",
         "layer",
         "editing"
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
      myAttributes.put("color", bg);
      myAttributes.put("passable", passable);
      myAttributes.put("layer", "background");
      myAttributes.put("editing", Boolean.FALSE);

      myRow = row;
      myCol = col;
      myChildren = new LinkedList<Tickable>();
   }

   @Override
   public void draw(Graphics pen) {
      if ( myAttributes.get("layer").equals("background") ||
            myAttributes.get("editing").equals(Boolean.TRUE) ) {
         pen.setColor((Color) myAttributes.get("color"));
         pen.fillRect(myRect.x, myRect.y, myRect.width, myRect.height);
         for ( DrawUpdatable child : myChildren ) {
            child.draw(pen);
         }
      } else if ( myAttributes.get("layer").equals("foreground") ) {
         for ( DrawUpdatable child : myChildren ) {
            child.draw(pen);
         }
         pen.setColor((Color) myAttributes.get("color"));
         pen.fillRect(myRect.x, myRect.y, myRect.width, myRect.height);
      } else {
         throw new IllegalStateException("No layer attribute set.");
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
      myAttributes.remove(name);
   }

   public boolean hasAttribute(String name) {
      return (null != myAttributes.get(name));
   }

   public Object getAttribute(String name) {
      // System.out.println("Use tree for this in Tile.java");
      for ( String perm : myPermAttributes ) {
         if ( perm.equals(name) ) {
            String mssg = "Use getter for attribute " + name + ".";
            throw new IllegalArgumentException(mssg);
         }
      }

      return myAttributes.get(name);
   }

   public void setAttribute(String name, Object obj) {
      for ( String perm : myPermAttributes ) {
         if ( perm.equals(name) ) {
            String mssg = "Use setter for attribute " + name + ".";
            throw new IllegalArgumentException(mssg);
         }
      }

      myAttributes.put(name, obj);
   }

   // accessor methods for permanent attributes
   public Color getColor() { return (Color) myAttributes.get("color"); }
   public void setColor(Color color) { myAttributes.put("color", color); }
   public boolean isPassable() { return (Boolean) myAttributes.get("passable"); }
   public void setPassable(boolean passable) { myAttributes.put("passable", passable); }
   public boolean isForeground() { return (Boolean) myAttributes.get("layer").equals("foreground"); }
   public void setIsForeground() { myAttributes.get("layer"); }
   public boolean isEditing() { return (Boolean) myAttributes.get("ediiting"); }
   public void setIsEditing(boolean editing) { myAttributes.put("editing", editing); }

   // other accessor methods
   @Override public Collection<Tickable> getComponents() { return myChildren; }
   public HashMap<String, Object> getAttributes(String name) { return myAttributes; }
   public void setAttributes(HashMap<String, Object> attributes) { myAttributes = attributes; }
   public Grid getGrid() { return myGrid; }
   public int getRow() { return myRow; }
   public int getCol() { return myCol; }
   public Rectangle getRect() { return myRect; }
   public void setRect(Rectangle rect) { myRect = rect; }
}