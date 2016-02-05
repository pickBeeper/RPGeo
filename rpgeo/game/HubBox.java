package rpgeo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import gfm.gui.GUIBox;

// TODO: Auto-generated Javadoc
/**
 * The Class HubBox.
 */
public class HubBox extends GUIBox {

   /**
    * Instantiates a new hub box.
    *
    * @param box the box
    * @param color the color
    * @param minOpacity the min opacity
    * @param maxOpacity the max opacity
    * @param opacitySpeed the opacity speed
    */
   public HubBox(Rectangle box, Color color, int minOpacity, int maxOpacity, int opacitySpeed) {
      super(box, color, minOpacity, maxOpacity, opacitySpeed);
   }

   /* (non-Javadoc)
    * @see gfm.gui.GUIComponentAdapter#mouseMoved(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseMoved(MouseEvent event) {
      if ( contains(event) ) {
         activate();
      } else {
         deactivate();
      }
   }

   /* (non-Javadoc)
    * @see gfm.gui.GUIComponentAdapter#mouseDragged(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseDragged(MouseEvent event) {
      if ( contains(event) ) {
         activate();
      } else {
         deactivate();
      }

   }

   /* (non-Javadoc)
    * @see gfm.gui.GUIComponentAdapter#mouseExited(java.awt.event.MouseEvent)
    */
   @Override
   public void mouseExited(MouseEvent event) {
      deactivate();
   }
}
