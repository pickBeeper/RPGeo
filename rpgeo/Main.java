package rpgeo;

import gfm.Game;
import gfm.Macro;
import gfm.gamestate.GameState;

public class Main {

   private static int gameWidth = 640;
   private static int gameHeight = 480;

   private static int drawWidth = (int) (1.5 * gameWidth);
   private static int drawHeight = (int) (1.5 * gameHeight);
   
   public static void main(String[] args) {
      Game game = new Game("RPGeo", gameWidth, gameHeight, drawWidth, drawHeight);
      game.setGameState("test");

      GameState test = new Test(game);
      game.addGameState(test);

      Macro gameGui = new GameGUI(game);
      game.addMacro(gameGui);

      game.start();
   }
}