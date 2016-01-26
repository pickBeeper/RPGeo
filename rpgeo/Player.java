package rpgeo;

import java.awt.Color;
import java.awt.Graphics;

import gfm.util.Vec2;

public class Player extends Mob {
   private int myRadius;
   private Color myColor;

   public Player(String name, Vec2 pos, int radius, Color color) {
     setName(name);
     setPos(pos);
     myRadius = radius;
     myColor = color;
   }

   public Color getColor() { return myColor; }
   public void setColor(Color color) { myColor = color; }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(myColor);
      int x = (int) (getPos().getX() - myRadius);
      int y = (int) (getPos().getY() - myRadius);
      pen.fillOval(x, y, 2 * myRadius, 2 * myRadius);
   }

   @Override
   public void update() {
      // TODO Auto-generated method stub
      
   }
}