package rpgeo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;

import gfm.DrawUpdatable;
import gfm.GameComponent;

public class Tile implements GameComponent<DrawUpdatable> {
   private Grid myGrid;
   private Rectangle myRect;
   private Color myBackground;
   private LinkedList<DrawUpdatable> myChildren;

   public Tile(Grid grid, Rectangle rect, Color background) {
      myGrid = grid;
      myRect = rect;
      myBackground = background;
      myChildren = new LinkedList<DrawUpdatable>();
   }

   @Override
   public void draw(Graphics pen) {
      pen.setColor(myBackground);
      pen.fillRect(myRect.x, myRect.y, myRect.width, myRect.height);

      for ( DrawUpdatable child : myChildren ) {
         child.draw(pen);
      }
   }

   @Override
   public void update() {
      for ( DrawUpdatable child : myChildren ) {
         child.update();
      }
   }

   public Rectangle getRect() { return myRect; }
   public void setRect(Rectangle rect) { myRect = rect; }


   @Override
   public void addComponent(DrawUpdatable toAdd) {
      myChildren.add(toAdd);
   }

   @Override
   public void removeComponent(DrawUpdatable toRemove) {
      myChildren.remove(toRemove);
   }

   @Override
   public Collection<DrawUpdatable> getComponents() {
      // TODO Auto-generated method stub
      return null;
   }
}