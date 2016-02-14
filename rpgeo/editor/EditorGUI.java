package rpgeo.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import gfm.gui.BasicButton;
import gfm.gui.GUIBox;
import gfm.gui.GUIManager;
import gfm.util.Vec2;
import rpgeo.Grid;
import rpgeo.Tickable;

public class EditorGUI {
   private Painter myPainter;
   private Rectangle myGUIBounds;
   private GUIBox myBackground;
   private ModeChangeButton myPencilButton;
   private ModeChangeButton myFillButton;
   private ModeChangeButton myPickButton;
   private PassableButton myPassableButton;
   private LayerButton myLayerButton;
   private SaveButton mySaveButton;
   private LoadButton myLoadButton;
   private ColorPicker myColorPicker;

   public EditorGUI(Painter painter, Rectangle guiBounds) {
      myPainter = painter;
      myGUIBounds = guiBounds;
      makeGUI();
   }

   public void makeGUI() {
      Color guiBgColor = new Color(200, 200, 200);
      myBackground = new GUIBox(myGUIBounds, guiBgColor, 35, 70, 6) {
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
      Vec2 buttonSize = new Vec2(90, 35);

      myPencilButton = new ModeChangeButton(myPainter, "pencil",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      myFillButton = new ModeChangeButton(myPainter, "fill",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      myPickButton = new ModeChangeButton(myPainter, "picker",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      myPassableButton = new PassableButton(myPainter, "passable",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      myLayerButton = new LayerButton(myPainter, "background",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      mySaveButton = new SaveButton(myPainter, "save",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);
      myLoadButton = new LoadButton(myPainter, "load",
            new Color(0, 0, 0, 0), buttonFont, new Vec2(), buttonSize);

      // align buttons
      myBackground.alignMiddleLeft(myPencilButton);
      myBackground.alignMiddleLeft(myFillButton);
      myBackground.alignMiddleLeft(myPickButton);
      myBackground.alignMiddleLeft(myPassableButton);
      myBackground.alignMiddleLeft(myLayerButton);
      myBackground.alignMiddleLeft(mySaveButton);
      myBackground.alignMiddleLeft(myLoadButton);

      myPencilButton.getPos().addX(0);
      myFillButton.getPos().addX(0);
      myPickButton.getPos().addX(100);
      myPassableButton.getPos().addX(100);
      myLayerButton.getPos().addX(200);
      mySaveButton.getPos().addX(200);
      myLoadButton.getPos().addX(300);

      myPencilButton.getPos().addY(18);
      myFillButton.getPos().addY(-18);
      myPickButton.getPos().addY(18);
      myPassableButton.getPos().addY(-18);
      myLayerButton.getPos().addY(-18);
      mySaveButton.getPos().addY(18);
      myLoadButton.getPos().addY(-18);

      // make colorPicker
      int x = (int) (myGUIBounds.getX() + 3 * myGUIBounds.getWidth() / 5);
      int y = (int) myGUIBounds.getY() + 4;
      int width = (int) (2 * myGUIBounds.getWidth() / 5 - 16);
      int height = (int) myGUIBounds.getHeight() - 8;
      myColorPicker = new ColorPicker(x, y, width, height);
   }

   public void register(GUIManager manager) {
      manager.addGUIComponent(myBackground);
      manager.addGUIComponent(myColorPicker);
      manager.addButton(myPencilButton);
      manager.addButton(myFillButton);
      manager.addButton(myPickButton);
      manager.addButton(myPassableButton);
      manager.addButton(myLayerButton);
      manager.addButton(mySaveButton);
      manager.addButton(myLoadButton);
   }

   public ColorPicker getColorPicker() {
      return myColorPicker;
   }

   public Painter getPainter() { return myPainter; }
   public void setPainter(Painter painter) { myPainter = painter; }
}

class LoadButton extends BasicButton {
   public LoadButton(Painter painter, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {}
      }, text, bodyColor, new Color(150, 150, 150), font, position, size);
   }
}

class SaveButton extends BasicButton {
   public SaveButton(Painter painter, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(new SaveListener(painter), text, bodyColor, new Color(150, 150, 150), font, position, size);
   }
}

class PassableButton extends BasicButton {
   public PassableButton(Painter painter, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(null, text, bodyColor, new Color(150, 150, 150), font, position, size);
      setAction(new PassableListener(painter, this));
   }
}

class LayerButton extends BasicButton {
   public LayerButton(Painter painter, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(null, text, bodyColor, new Color(150, 150, 150), font, position, size);
      setAction(new LayerListener(painter, this));
   }
}

class ModeChangeButton extends BasicButton {
   public ModeChangeButton(Painter painter, String text,
         Color bodyColor, Font font, Vec2 position, Vec2 size) {
      super(new ModeChangeListener(painter, text), text, bodyColor,
            new Color(150, 150, 150), font, position, size);
   }
}
class SaveListener implements ActionListener {
   private Painter myPainter;

   public SaveListener(Painter painter) {
      myPainter = painter;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String name = JOptionPane.showInputDialog("Save as: ");

      Grid grid = (Grid) myPainter.getGrid();

      int rows = grid.getTiles().length;
      int cols = grid.getTiles()[ 0 ].length;
      AttributeDisplay[][] displays = new AttributeDisplay[ rows ][ cols ];
      for ( int r = 0; r < grid.getTiles().length; r++ ) {
         for ( int c = 0; c < grid.getTiles()[0].length; c++ ) {
            grid.getTiles()[ r ][ c ].setAttribute("editing", Boolean.FALSE);
            for ( Tickable t : grid.getTiles()[ r ][ c ].getComponents() ) {
               if ( t instanceof AttributeDisplay ) {
                  displays[ r ][ c ] = (AttributeDisplay) t;
                  grid.getTiles()[ r ][ c ].removeComponent(t);
                  break;
               }
            }
         }
      }

      try {
         FileOutputStream fileOut = new FileOutputStream(new File(name));
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(grid);
      } catch (IOException e1) {
         e1.printStackTrace();
      }

      for ( int r = 0; r < displays.length; r++ ) {
         for ( int c = 0; c < displays[0].length; c++ ) {
            grid.getTiles()[ r ][ c ].addComponent(displays[ r ][ c ]);
            grid.getTiles()[ r ][ c ].setAttribute("editing", Boolean.TRUE);
         }
      }
   }
}

class PassableListener implements ActionListener {
   private Painter myPainter;
   private BasicButton myButton;

   public PassableListener(Painter painter, BasicButton button) {
      myPainter = painter;
      myButton = button;
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      Boolean passable = !(Boolean) myPainter.getAttribute("passable");
      myPainter.setAttribute("passable", passable);

      if ( passable ) {
         myButton.setText("passable");
      } else {
         myButton.setText("blocked");
      }
   }
}

class LayerListener implements ActionListener {
   private Painter myPainter;
   private BasicButton myButton;

   public LayerListener(Painter painter, BasicButton button) {
      myPainter = painter;
      myButton = button;
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      String layer = (String) myPainter.getAttribute("layer");

      if ( layer.equals("background") ) {
         myButton.setText("foreground");
         myPainter.setAttribute("layer", "foreground");
      } else if ( layer.equals("foreground") ){
         myButton.setText("background");
         myPainter.setAttribute("layer", "background");
      }
   }
}

class ModeChangeListener implements ActionListener {
   private Painter myPainter;
   private String myMode;

   public ModeChangeListener(Painter painter, String mode) {
      myPainter = painter;
      myMode = mode;
   }

   @Override
   public void actionPerformed(ActionEvent event) {
      myPainter.setEditMode(myMode);
   }
}