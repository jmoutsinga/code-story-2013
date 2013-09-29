/**
 * 
 */
package net.codestory.jajascript.ui.chronoline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jmoutsinga
 * 
 */
public class ChronolineData {

	
	private String refDate;
    private List<ChronolineEvent> events;

    public ChronolineData(Date refDate) {
    	
    	this.refDate = new SimpleDateFormat("yyyy/MM/dd").format(refDate);
        events = new ArrayList<ChronolineEvent>();
    }

    public void addEvent(ChronolineEvent nextEvent) {
        events.add(nextEvent);
    }

}
