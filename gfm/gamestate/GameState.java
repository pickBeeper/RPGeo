package gfm.gamestate;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import gfm.Game;
import gfm.KeyListener;
import gfm.MouseListener;
import gfm.gui.GUIManager;
import gfm.sound.SoundManager;

public abstract class GameState {
   private Game myGame;
   private String myGameMode;
   private GUIManager myGUIManager;

   private KeyListener myKeyListener;
   private MouseListener myMouseListener;

   public GameState(Game game) {
      this(game, "");
      myGameMode = getClass().getName().toLowerCase();
      myGameMode = myGameMode.substring(myGameMode.lastIndexOf(".") + 1);
   }

   public GameState(Game game, String gameMode) {
      myGame = game;
      myGameMode = gameMode;
      myGUIManager = new GUIManager(game);
      myKeyListener = new KeyListener(this);
      myMouseListener = new MouseListener(this);
      init();
      initGUI();
   }

   public String getGameMode() { return myGameMode; }
   public Game getGame() { return myGame; }
   public void setGame(Game game) { myGame = game; }
   public GUIManager getGUIManager() { return myGUIManager; }
   public KeyAdapter getKeyListener() { return myKeyListener; }
   public MouseAdapter getMouseListener() { return myMouseListener; }
   public SoundManager getSoundManager() { return myGame.getSoundManager(); }
   public int getWidth() { return myGame.getWidth(); }
   public int getHeight() { return myGame.getHeight(); }

   public void drawOverMacro(Graphics pen) {};

   public abstract void draw(Graphics pen);
   public abstract void update();

   public abstract void initGUI();
   public abstract void init();

   public abstract void keyPressed(KeyEvent event);
   public abstract void keyReleased(KeyEvent event);
   public abstract void keyTyped(KeyEvent event);

   public abstract void mouseClicked(MouseEvent event);
   public abstract void mouseDragged(MouseEvent event);
   public abstract void mouseEntered(MouseEvent event);
   public abstract void mouseExited(MouseEvent event);
   public abstract void mouseMoved(MouseEvent event);
   public abstract void mousePressed(MouseEvent event);
   public abstract void mouseReleased(MouseEvent event);
   public abstract void mouseWheelMoved(MouseWheelEvent event);
}