package rpgeo;

public class TestPlace extends Place {

   public TestPlace(World world, int width, int height, String name) {
      super(world, width, height, name);
      Player player = getWorld().getPlayer();
      player.setTile(getGrid().getTile(0, 0));
      getGrid().getTile(0, 0).addComponent(player);
   }
}
