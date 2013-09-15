/**
 * 
 */
package net.codestory.jajascript.ui.chronoline;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jmoutsinga
 * 
 */
public class DatePeriod {

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    private String startDate;
    private String endDate;

    public DatePeriod(Date start, Date end) {
        this.startDate = new SimpleDateFormat(DATE_FORMAT).format(start);
        this.endDate = new SimpleDateFormat(DATE_FORMAT).format(end);
    }
}
