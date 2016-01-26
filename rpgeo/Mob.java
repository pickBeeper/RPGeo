package rpgeo;

import java.awt.Graphics;

import gfm.util.ControllableAdapter;
import gfm.util.Vec2;

public abstract class Mob extends ControllableAdapter {
   private Vec2 myPos;
   private String myName;

   public abstract void draw(Graphics pen);
   public abstract void update();

   public String getName() { return myName; }
   public void setName(String name) { myName = name; }
   public Vec2 getPos() { return myPos; }
   public void setPos(Vec2 pos) { myPos = pos; }
}
