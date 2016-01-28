package rpgeo;

import java.awt.Color;
import java.awt.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends Mob {

   /** The my color. */
   private Color myColor;

   /**
    * Instantiates a new player.
    *
    * @param name the name
    * @param tile the tile
    * @param color the color
    */
   public Player(String name, Tile tile, Color color) {
      setName(name);
      setTile(tile);
      myColor = color;
   }

   /**
    * Gets the color.
    *
    * @return the color
    */
   public Color getColor() { return myColor; }

   /**
    * Sets the color.
    *
    * @param color the new color
    */
   public void setColor(Color color) { myColor = color; }

   /* (non-Javadoc)
    * @see gfm.GameComponentAdapter#draw(java.awt.Graphics)
    */
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