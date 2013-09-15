/**
 * 
 */
package net.codestory.jajascript.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import net.codestory.jajascript.domain.Path;
import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class Timeline extends Observable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int begin;
    private final int end;
    private final int totalRentalWishes;
    private Map<StartIndex, List<RentalWish>> byStartHour;
    private TreeMap<Index, RentalWish> byModification;
    private int index;

    public Timeline(int totalRentalWishes, int begin, int end) {
        this.totalRentalWishes = totalRentalWishes;
        this.begin = begin;
        this.end = end;
        byStartHour = createInternalRepresentation();
        byModification = new TreeMap<Index, RentalWish>();
        index = 0;
    }

    private Map<StartIndex, List<RentalWish>> createInternalRepresentation() {
        Map<StartIndex, List<RentalWish>> result = new HashMap<StartIndex, List<RentalWish>>();
        for (int i = begin; i <= end; i++) {
            result.put(new StartIndex(i), new ArrayList<RentalWish>());
        }
        return result;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public int getTotalRentalWishes() {
        return totalRentalWishes;
    }

    public void addPath(Path path) {
        logger.debug("Adding path {} to the time line", path);

        List<RentalWish> flights = path.getFlights();

        for (RentalWish rentalWish : flights) {
            addFlight(rentalWish);
        }

    }

    public void addFlight(RentalWish rentalWish) {
        logger.debug("Adding flight {} to the time line", rentalWish);
        byStartHour.get(new StartIndex(rentalWish.getStartHour())).add(rentalWish);
        byModification.put(new Index(index++), rentalWish);
        setChanged();
        notifyObservers(rentalWish);
    }

    public RentalWish getLastFlightAdded() {
        return byModification.lastEntry().getValue();
    }

    public boolean hasChanged() {
        return super.hasChanged();
    }

    public Iterator<RentalWish> flightIterator() {
        return byModification.values().iterator();
    }
}
