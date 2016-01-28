package rpgeo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gfm.gui.GUIBox;

public class HubBox extends GUIBox {

   public HubBox(Rectangle box, Color color, int minOpacity, int maxOpacity, int opacitySpeed) {
      super(box, color, minOpacity, maxOpacity, opacitySpeed);
   }

   @Override
   public void mouseMoved(MouseEvent event) {
      if ( contains(event) ) {
         activate();
      } else {
         deactivate();
      }
   }

   @Override
   public void mouseDragged(MouseEvent event) {
      if ( contains(event) ) {
         activate();
      } else {
         deactivate();
      }

   }

   @Override
   public void mouseExited(MouseEvent event) {
      deactivate();
   }
}
