package rpgeo;

import javax.swing.JOptionPane;

import gfm.Game;
import gfm.gamestate.GameState;
import rpgeo.editor.Editor;
import rpgeo.game.Play;

public class Main {
   // dimensions for game image
   private static int gameWidth = 640;
   private static int gameHeight = 480;

   // dimensions to draw game at
   private static int drawWidth = (int) (1.5 * gameWidth);
   private static int drawHeight = (int) (1.5 * gameHeight);

   public static void main(String[] args) {
      Game game = new Game("RPGeo", gameWidth, gameHeight, drawWidth, drawHeight);

      GameState play = new Play(game);
      GameState editor = new Editor(game);

      game.addGameState(play);
      game.addGameState(editor);

      String state = JOptionPane.showInputDialog("GameState: ");
      if ( state == null ) {
         System.exit(0);
      } else {
         game.setGameState(state);
      }

      game.start();
   }
}