package rpgeo;

import gfm.Game;
import gfm.gamestate.GameState;
import rpgeo.editor.Editor;

public class Main {
   private static int gameWidth = 640;
   private static int gameHeight = 480;

   private static int drawWidth = (int) (1.5 * gameWidth);
   private static int drawHeight = (int) (1.5 * gameHeight);

   public static void main(String[] args) {
      Game game = new Game("RPGeo", gameWidth, gameHeight, drawWidth, drawHeight);

      GameState play = new Play(game);
      GameState editor = new Editor(game);

      game.addGameState(play);
      game.setGameState("play");
      game.addGameState(editor);
      game.start();
   }
}