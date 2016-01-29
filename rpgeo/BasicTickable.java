package rpgeo;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import gfm.GameComponentAdapter;

public abstract class BasicTickable extends GameComponentAdapter<Tickable> implements Tickable {
   private HashMap<String, LinkedList<ActionListener>> myQueuedActions =
         new HashMap<String, LinkedList<ActionListener>>();

   @Override
   public void queueForTick(String type, ActionListener toQueue) {
      LinkedList<ActionListener> toAddTo = myQueuedActions.get(type);
      if ( toAddTo == null ) {
         toAddTo = new LinkedList<ActionListener>();
         myQueuedActions.put(type, toAddTo);
      }

      toAddTo.push(toQueue);
   }

   public void clearAndDoQueue() {
      LinkedList<ActionListener> actions;
      for ( String key : myQueuedActions.keySet() ) {
         actions = myQueuedActions.get(key);
         while ( !actions.isEmpty() ) {
            actions.pop().actionPerformed(null);
         }
      }

      myQueuedActions =
            new HashMap<String, LinkedList<ActionListener>>();
   }

   public void clearQueuedType(String type) {
      myQueuedActions.remove(type);
      LinkedList<ActionListener> cleared = new LinkedList<ActionListener>();
      myQueuedActions.put(type, cleared);
   }

   @Override
   public HashMap<String, LinkedList<ActionListener>> getQueuedActions() {
      return myQueuedActions;
   }
}