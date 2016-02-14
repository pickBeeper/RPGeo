package rpgeo.editor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Stack;

import rpgeo.Grid;
import rpgeo.Tile;

public class Painter {
   private Grid myGrid;
   private EditorGUI myEditorGUI;
   private String myEditMode;
   private HashMap<String, Object> myAttributes;
   private boolean myIsFilling;

   public Painter(Grid grid) {
      myGrid = grid;

      myAttributes = new HashMap<String, Object>();
      myAttributes.put("layer", "background");
      myAttributes.put("color", Color.white);
      myAttributes.put("passable", Boolean.TRUE);
      myAttributes.put("editing", Boolean.TRUE);

      myEditMode = "pencil";
   }

   public void mouseDragged(MouseEvent event) {
      if ( myEditMode.equals("pencil") ) {
         changeTile(event);
      } else if ( myEditMode.equals("picker") ) {
         pickColor(event);
      } else if ( myEditMode.equals("fill") ) {
         fillColor(event);
      }
   }

   public void mousePressed(MouseEvent event) {
      if ( myEditMode.equals("pencil") ) {
         changeTile(event);
      } else if ( myEditMode.equals("picker") ) {
         pickColor(event);
      } else if ( myEditMode.equals("fill") ) {
         fillColor(event);
      }
   }

   @SuppressWarnings("unchecked")
   public void changeAttributes(Tile tile) {
      Color c = myEditorGUI.getColorPicker().getColor();
      myAttributes.put("color", c);

      if ( tile != null ) {
         tile.setAttributes(
               (HashMap<String, Object>) myAttributes.clone());
      }
   }

   public void changeTile(MouseEvent event) {
      changeAttributes(tileAtEvent(event));
   }

   public void pickColor(MouseEvent event) {
      Tile tile = tileAtEvent(event);
      if ( tile != null ) {
         Color picked = tileAtEvent(event).getColor();
         if ( picked != null ) {
            myEditorGUI.getColorPicker().setColor(picked);
            myEditMode = "pencil";
         }
      }
   }
   public void fillColor(MouseEvent event) {
      Tile start = tileAtEvent(event);
      if ( start != null) {
         Stack<Tile> toFill = new Stack<Tile>();
         toFill.add(start);
         Color original = start.getColor();

         Color picked = myEditorGUI.getColorPicker().getColor();
         if ( original.equals(picked) ) {
            return;
         }

         Tile curr;
         while ( !toFill.isEmpty() ) {
            curr = toFill.pop();
            int row = curr.getRow();
            int col = curr.getCol();

            if ( myGrid.inBounds(row + 1, col) ) {
               Tile down = myGrid.getTile(row + 1, col);
               if ( down.getColor().equals(original) ) {
                  toFill.push(down);
               }
            }
            if ( myGrid.inBounds(row - 1, col) ) {
               Tile up = myGrid.getTile(row - 1, col);
               if ( up.getColor().equals(original) ) {
                  toFill.push(up);
               }
            }
            if ( myGrid.inBounds(row, col + 1) ) {
               Tile right = myGrid.getTile(row, col + 1);
               if ( right.getColor().equals(original) ) {
                  toFill.push(right);
               }
            }
            if ( myGrid.inBounds(row, col - 1) ) {
               Tile left = myGrid.getTile(row, col - 1);
               if ( left.getColor().equals(original) ) {
                  toFill.push(left);
               }
            }

            changeAttributes(curr);
         }
      }
   }

   public Tile tileAtEvent(MouseEvent event) {
      Rectangle gridBounds = myGrid.getBounds();
      Point point = event.getPoint();
      if ( gridBounds.contains(event.getPoint()) ) {
         Tile[][] tiles = myGrid.getTiles();
         // better way
         for ( int r = 0; r < tiles.length; r++ ) {
            for ( int c = 0; c < tiles[0].length; c++ ) {
               if ( tiles[ r ][ c ].getRect().contains(point) ) {
                  return tiles[ r ][ c ];
               }
            }
         }
      }

      return null;
   }

   public HashMap<String, Object> getAttributes(String name) { return myAttributes; }
   public void setAttributes(HashMap<String, Object> attributes) { myAttributes = attributes; }
   public Object getAttribute(String name) { return myAttributes.get(name); }
   public void setAttribute(String name, Object obj) { myAttributes.put(name, obj); }
   public String getEditMode() { return myEditMode; }
   public void setEditMode(String mode) { myEditMode = mode; }
   public EditorGUI getEditorGUI() { return myEditorGUI; }
   public void setEditorGUI(EditorGUI editorGUI) { myEditorGUI = editorGUI; }
   public Grid getGrid() { return myGrid; }
   public void setGrid(Grid grid) { myGrid = grid; }
}