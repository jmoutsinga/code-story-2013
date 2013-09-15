/**
 * 
 */
package net.codestory.jajascript.ui.chronoline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jmoutsinga
 * 
 */
public class ChronolineData {

    private List<ChronolineEvent> events;

    public ChronolineData() {
        events = new ArrayList<ChronolineEvent>();
    }

    public void addEvent(ChronolineEvent nextEvent) {
        events.add(nextEvent);
    }

}
