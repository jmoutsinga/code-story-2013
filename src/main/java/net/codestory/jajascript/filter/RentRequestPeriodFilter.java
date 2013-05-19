/**
 * 
 */
package net.codestory.jajascript.filter;

import java.util.List;
import java.util.TreeMap;

import net.codestory.jajascript.domain.Period;
import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class RentRequestPeriodFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentRequests;

    public RentRequestPeriodFilter(List<RentalWish> rentRequests) {
        this.rentRequests = rentRequests;
    }

    public TreeMap<Period, RentalWish> doFilter() {
        logger.debug("Filtering the flights request...");
        TreeMap<Period, RentalWish> bestRentRequestByPeriod = new TreeMap<>();
        for (RentalWish rentRequest : rentRequests) {
            RentalWish current = bestRentRequestByPeriod.get(rentRequest.getPeriod());
            if (current == null || current.getPrice() < rentRequest.getPrice()) {
                bestRentRequestByPeriod.put(rentRequest.getPeriod(), rentRequest);
            }
        }
        logger.info("After filtering, keeping {} best rent request", bestRentRequestByPeriod.size());
        return bestRentRequestByPeriod;
    }
}
