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

      String state = JOptionPane.showInputDialog("GameState: ");
      if ( state.equals("play") ) {
         GameState play = new Play(game);
         game.addGameState(play);
         game.setGameState("play");
      } else if ( state.equals("edit") || state.equals("editor") ) {
         GameState editor = new Editor(game);
         game.addGameState(editor);
         game.setGameState("editor");
      } else {
         System.exit(0);
      }

      game.start();
      //System.out.println("Const. follow");
   }
}