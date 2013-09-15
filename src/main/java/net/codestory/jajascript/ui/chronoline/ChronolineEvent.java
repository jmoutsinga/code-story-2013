/**
 * 
 */
package net.codestory.jajascript.ui.chronoline;

/**
 * @author jmoutsinga
 * 
 */
public class ChronolineEvent {

    private DatePeriod period;
    private String title;

    public ChronolineEvent(String eventTitle, DatePeriod eventPeriod) {
        title = eventTitle;
        period = eventPeriod;
    }

}
