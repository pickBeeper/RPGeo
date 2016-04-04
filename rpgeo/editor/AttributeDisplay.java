package rpgeo.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gfm.util.StringDraw;
import rpgeo.Tile;
import rpgeo.game.BasicTickable;

public class AttributeDisplay extends BasicTickable {
   private Tile myTile;

   public AttributeDisplay(Tile tile) {
      myTile = tile;
   }

   @Override
   public void draw(Graphics pen) {
      String toDisplay = "";

      if ( myTile.isPassable() ) {
         toDisplay += "p";
      } else {
         toDisplay += "";
      }

      if ( myTile.isForeground() ) {
         toDisplay += "f";
      } else {
         toDisplay += "b";
      }

      pen.setFont(new Font("ariel", Font.PLAIN, 9));
      int x = (int) myTile.getRect().getCenterX();
      int y = (int) myTile.getRect().getCenterY();
      pen.setColor(Color.black);
      StringDraw.drawStringCenter(pen, toDisplay, x - 1, y - 1);
      pen.setColor(Color.white);
      StringDraw.drawStringCenter(pen, toDisplay, x + 1, y + 1);
   }

   @Override
   public void update() { }

   @Override
   public void tick() { }
}
