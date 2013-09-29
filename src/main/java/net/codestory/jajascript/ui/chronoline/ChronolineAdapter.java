package net.codestory.jajascript.ui.chronoline;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.ui.Timeline;

public class ChronolineAdapter {

    private Calendar refDate;

    public ChronolineAdapter(int timelineBeginIndex) {
        this.refDate = computeRefDate(timelineBeginIndex);
    }

    private Calendar computeRefDate(int timelineBeginIndex) {
        Calendar refDate = Calendar.getInstance();
        refDate.set(Calendar.HOUR, 0);
        refDate.set(Calendar.MINUTE, 0);
        refDate.set(Calendar.SECOND, 0);
        refDate.set(Calendar.MILLISECOND, 0);
        refDate.add(Calendar.DATE, timelineBeginIndex);
        return refDate;

    }

    public ChronolineData toChronolineData(Timeline timeline) {

        ChronolineData result = new ChronolineData(getRefDate().getTime());
        Iterator<RentalWish> iterator = timeline.flightIterator();
        while (iterator.hasNext()) {
            RentalWish next = iterator.next();
            String eventTitle = computeTitle(next);
            DatePeriod eventPeriod = computePeriod(next);

            ChronolineEvent nextEvent = new ChronolineEvent(eventTitle, eventPeriod);
            result.addEvent(nextEvent);
        }
        return result;
    }

    private Calendar getRefDate() {
        return refDate;
    }

    private String computeTitle(RentalWish next) {
        return new StringBuilder().append(next.getCompanyName()).append(" [$ ").append(next.getPrice()).append("]").toString();
    }

    private DatePeriod computePeriod(RentalWish flight) {
        int start = flight.getStartHour();
        int duration = flight.getDuration();
        Calendar refDate = getRefDate();
        refDate.add(Calendar.DATE, start);
        Date startDate = refDate.getTime();
        refDate.add(Calendar.DATE, duration);
        Date endDate = refDate.getTime();
        return new DatePeriod(startDate, endDate);
    }
}
