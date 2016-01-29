package rpgeo;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import gfm.DrawUpdatable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Tickable.
 */
public interface Tickable extends DrawUpdatable {

   /**
    * Call commands queued for tick.
    */
   void tick();

   /**
    * Queue a command to be called next tick.
    *
    * @param toQueue the to queue
    */
   void queueForTick(String type, ActionListener toQueue);

   HashMap<String, LinkedList<ActionListener>> getQueuedActions();
}