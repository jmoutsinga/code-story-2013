/**
 * 
 */
package net.codestory.jajascript.filter;

import java.util.List;
import java.util.TreeMap;

import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class StartHourFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<RentalWish> rentRequests;

    public StartHourFilter(List<RentalWish> rentRequests) {
        this.rentRequests = rentRequests;
    }

    public TreeMap<Integer, RentalWish> doFilter() {
        TreeMap<Integer, RentalWish> filter = new TreeMap<>();
        for (RentalWish rentRequest : rentRequests) {

            RentalWish current = filter.get(rentRequest.getStartHour());
            if (current == null || current.getPrice() < rentRequest.getPrice()) {
                filter.put(rentRequest.getStartHour(), rentRequest);
            }
        }
        logger.info("After filter, keeping {} rent request in optimizer", filter.size());
        return filter;
    }
}
