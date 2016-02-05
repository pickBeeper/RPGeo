package rpgeo.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Stack;

import gfm.Game;
import gfm.gamestate.GameState;
import gfm.gui.BasicButton;
import gfm.gui.GUIBox;
import gfm.util.Vec2;
import rpgeo.Grid;
import rpgeo.Tile;

public class Editor extends GameState {
   private Grid myGrid;
   private Rectangle myGUIBounds;
   private ColorPicker myColorPicker;
   private String myEditMode;
   private boolean myIsFilling;

   public Editor(Game game) {
      super(game);
   }

   public Editor(Game game, String stateName) {
      super(game, stateName);
   }

   @Override
   public void draw(Graphics pen) {
      myGrid.draw(pen);
      getGUIManager().draw(pen);
   }

   @Override
   public void update() {
      getGUIManager().update();
   }

   @Override
   public void initGUI() {
      myGUIBounds = new Rectangle();
      myGUIBounds.setLocation(4, 8 + getHeight() * 4 / 5);
      myGUIBounds.setSize(getWidth() - 8, getHeight() / 5 - 12);

      Color guiBgColor = new Color(200, 200, 200);
      GUIBox background = new GUIBox(myGUIBounds, guiBgColor, 35, 70, 6) {
         @Override
         public void mouseMoved(MouseEvent event) {
            if ( contains(event) ) {
               activate();
            } else {
               deactivate();
            }
         }
         @Override
         public void mouseClicked(MouseEvent event) {
            if ( contains(event) ) {
               activate();
            } else {
               deactivate();
            }
         }
      };

      // make buttons
      Font buttonFont = new Font("ariel", 1, 15);
      Vec2 buttonSize = new Vec2(60, 35);

      ModeChangeButton pencilButton =
            new ModeChangeButton(this, "pencil", new Color(0, 0, 0, 0),
                  buttonFont, new Vec2(), buttonSize);
      ModeChangeButton fillButton =
            new ModeChangeButton(this, "fill", new Color(0, 0, 0, 0),
                  buttonFont, new Vec2(), buttonSize);
      ModeChangeButton pickButton =
            new ModeChangeButton(this, "picker", new Color(0, 0, 0, 0),
                  buttonFont, new Vec2(), buttonSize);

      // align buttons
      background.alignMiddleLeft(pencilButton);
      pencilButton.getPos().addX(70);
      background.alignMiddleLeft(fillButton);
      fillButton.getPos().addX(140);
      background.alignMiddleLeft(pickButton);
      pickButton.getPos().addX(210);


      // make colorPicker
      int x = (int) (myGUIBounds.getX() + 3 * myGUIBounds.getWidth() / 5);
      int y = (int) myGUIBounds.getY() + 4;
      int width = (int) (2 * myGUIBounds.getWidth() / 5 - 16);
      int height = (int) myGUIBounds.getHeight() - 8;
      myColorPicker = new ColorPicker(x, y, width, height);

      // add gui components
      getGUIManager().addGUIComponent(background);
      getGUIManager().addGUIComponent(myColorPicker);
      getGUIManager().addButton(pencilButton);
      getGUIManager().addButton(fillButton);
      getGUIManager().addButton(pickButton);
   }

   @Override
   public void init() {
      Rectangle bounds = new Rectangle();
      bounds.setLocation(4, 4);
      bounds.setSize(getWidth()  - 8, getHeight() * 4 / 5);

      myGrid = new Grid(bounds, 30, 25);

      Tile[][] tiles = myGrid.getTiles();
      for ( int r = 0; r < tiles.length; r++ ) {
         for ( int c = 0; c < tiles[0].length; c++ ) {
            tiles[ r ][ c ].setBackground(Color.white);
         }
      }

      myEditMode = "pencil";

      myIsFilling = false;
   }

   public void changeColor(MouseEvent event) {
      Color color = myColorPicker.getColor();
      Tile tile = tileAtEvent(event);
      if ( tile != null ) {
         tile.setBackground(color);
      }
   }

   public void pickColor(MouseEvent event) {
      Tile tile = tileAtEvent(event);
      if ( tile != null ) {
         Color picked = tileAtEvent(event).getBackground();
         if ( picked != null ) {
            myColorPicker.setColor(picked);
            myEditMode = "pencil";
         }
      }
   }
   public void fillColor(MouseEvent event) {
      Tile start = tileAtEvent(event);
      if ( start != null) {
         Stack<Tile> toFill = new Stack<Tile>();
         toFill.add(start);
         Color original = start.getBackground();

         if ( original.equals(myColorPicker.getColor()) ) {
            return;
         }

         Tile curr;
         while ( !toFill.isEmpty() ) {
            curr = toFill.pop();
            int row = curr.getRow();
            int col = curr.getCol();

            if ( myGrid.inBounds(row + 1, col) ) {
               Tile down = myGrid.getTile(row + 1, col);
               if ( down.getBackground().equals(original) ) {
                  toFill.push(down);
               }
            }
            if ( myGrid.inBounds(row - 1, col) ) {
               Tile up = myGrid.getTile(row - 1, col);
               if ( up.getBackground().equals(original) ) {
                  toFill.push(up);
               }
            }
            if ( myGrid.inBounds(row, col + 1) ) {
               Tile right = myGrid.getTile(row, col + 1);
               if ( right.getBackground().equals(original) ) {
                  toFill.push(right);
               }
            }
            if ( myGrid.inBounds(row, col - 1) ) {
               Tile left = myGrid.getTile(row, col - 1);
               if ( left.getBackground().equals(original) ) {
                  toFill.push(left);
               }
            }
            curr.setBackground(myColorPicker.getColor());
         }
      }
   }

   public Tile tileAtEvent(MouseEvent event) {
      Rectangle gridBounds = myGrid.getBounds();
      Point point = event.getPoint();
      if ( gridBounds.contains(event.getPoint()) ) {
         Tile[][] tiles = myGrid.getTiles();
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

   @Override
   public void keyPressed(KeyEvent event) {
      getGUIManager().keyPressed(event);
   }
   @Override
   public void keyReleased(KeyEvent event) {
      getGUIManager().keyReleased(event);
   }
   @Override
   public void keyTyped(KeyEvent event) {
      getGUIManager().keyTyped(event);
   }

   @Override
   public void mouseClicked(MouseEvent event) {
      getGUIManager().mouseClicked(event);
   }
   @Override
   public void mouseDragged(MouseEvent event) {
      getGUIManager().mouseDragged(event);
      if ( myEditMode.equals("fill") ) {
         if ( !myIsFilling ) {
            myIsFilling = true;
            fillColor(event);
            myIsFilling = false;
         }
      } else if ( myEditMode.equals("pencil") ) {
         changeColor(event);
      } else if ( myEditMode.equals("picker") ) {
         pickColor(event);
      }
   }
   @Override
   public void mouseEntered(MouseEvent event) {
      getGUIManager().mouseEntered(event);
   }
   @Override
   public void mouseExited(MouseEvent event) {
      getGUIManager().mouseExited(event);
   }
   @Override
   public void mouseMoved(MouseEvent event) {
      getGUIManager().mouseMoved(event);
   }
   @Override
   public void mousePressed(MouseEvent event) {
      getGUIManager().mousePressed(event);
      if ( myEditMode.equals("fill") ) {
         if ( !myIsFilling ) {
            myIsFilling = true;
            fillColor(event);
            myIsFilling = false;
         }
      } else if ( myEditMode.equals("pencil") ) {
         changeColor(event);
      } else if ( myEditMode.equals("picker") ) {
         pickColor(event);
      }
   }
   @Override
   public void mouseReleased(MouseEvent event) {
      getGUIManager().mouseReleased(event);
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      getGUIManager().mouseWheelMoved(event);
   }

   public String getEditMode() { return myEditMode; }
   public void setEditMode(String mode) { myEditMode = mode; }
}

class EditorButton extends BasicButton {
   public EditorButton(ActionListener listener, Editor editor, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(listener, text, bodyColor, new Color(150, 150, 150), font, position, size);
   }
}

class ModeChangeButton extends BasicButton {
   public ModeChangeButton(Editor editor, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(new ModeChangeListener(editor, text), text, bodyColor, new Color(150, 150, 150), font, position, size);
   }
}

class ModeChangeListener implements ActionListener {
   private Editor myEditor;
   private String myMode;

   public ModeChangeListener(Editor editor, String mode) {
      myEditor = editor;
      myMode = mode;
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      myEditor.setEditMode(myMode);
   }
}