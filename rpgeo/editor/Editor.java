package rpgeo.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.gamestate.GameState;
import rpgeo.Grid;
import rpgeo.Tile;

public class Editor extends GameState {
   private Grid myGrid;
   private Painter myPainter;
   private EditorGUI myEditorGUI;

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
   public void init() {
      Rectangle bounds = new Rectangle();
      bounds.setLocation(4, 4);
      bounds.setSize(getWidth()  - 8, getHeight() * 4 / 5);

      myGrid = new Grid(bounds, 30, 25);

      Tile[][] tiles = myGrid.getTiles();
      for ( int r = 0; r < tiles.length; r++ ) {
         for ( int c = 0; c < tiles[0].length; c++ ) {
            tiles[ r ][ c ].setColor(Color.white);
            tiles[ r ][ c ].addComponent(
                  new AttributeDisplay(tiles[ r ][ c ]));
         }
      }

      myPainter = new Painter(myGrid);
      Rectangle guiBounds;
      guiBounds = new Rectangle();
      guiBounds.setLocation(4, 8 + getHeight() * 4 / 5);
      guiBounds.setSize(getWidth() - 8, getHeight() / 5 - 12);
      myEditorGUI = new EditorGUI(myPainter, guiBounds);
      myPainter.setEditorGUI(myEditorGUI);
   }

   @Override
   public void initGUI() {
      myEditorGUI.register(getGUIManager());
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
      myPainter.mouseDragged(event);
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
      myPainter.mousePressed(event);
   }
   @Override
   public void mouseReleased(MouseEvent event) {
      getGUIManager().mouseReleased(event);
   }
   @Override
   public void mouseWheelMoved(MouseWheelEvent event) {
      getGUIManager().mouseWheelMoved(event);
   }

   public Grid getGrid() { return myGrid; }
   public void setGrid(Grid grid) {
      myGrid = grid;
      myPainter.setGrid(myGrid);
   }
}