package rpgeo;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Mob {
   private Color myColor;

   public Player(String name, Tile tile, Color color) {
      setName(name);
      setTile(tile);
      myColor = color;
   }

   public Color getColor() { return myColor; }
   public void setColor(Color color) { myColor = color; }

   @Override
   public void draw(Graphics pen) {
      if ( getTile() == null) { return; }

      int x = getTile().getRect().x;
      int y = getTile().getRect().y;
      int width = getTile().getRect().width;
      int height = getTile().getRect().height;

      pen.setColor(myColor);
      pen.fillRect(x, y, width, height);
   }
}