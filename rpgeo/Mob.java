package rpgeo;

import gfm.GameComponentAdapter;

public abstract class Mob extends GameComponentAdapter {
   private Tile myTile;
   private String myName;

   public Tile getTile() { return myTile; }
   public void setTile(Tile tile) { myTile = tile; }
   public String getName() { return myName; }
   public void setName(String name) { myName = name; }
}
