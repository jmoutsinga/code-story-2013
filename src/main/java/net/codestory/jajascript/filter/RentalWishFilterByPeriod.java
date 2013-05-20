/**
 * 
 */
package net.codestory.jajascript.filter;

import java.util.ArrayList;
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
public class RentalWishFilterByPeriod {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentalWishes;

    public RentalWishFilterByPeriod(List<RentalWish> rentalWishes) {
        this.rentalWishes = rentalWishes;
    }

    public List<RentalWish> doFilter() {
        logger.debug("Filtering rental wishes request...");
        TreeMap<Period, RentalWish> bestRentRequestByPeriod = new TreeMap<>();
        for (RentalWish rentRequest : rentalWishes) {
            RentalWish current = bestRentRequestByPeriod.get(rentRequest.getPeriod());
            if (current == null || current.getPrice() < rentRequest.getPrice()) {
                bestRentRequestByPeriod.put(rentRequest.getPeriod(), rentRequest);
            }
        }
        logger.info("After filtering, keeping {} best rent request on {} initials", bestRentRequestByPeriod.size(), rentalWishes.size());
        return new ArrayList<RentalWish>(bestRentRequestByPeriod.values());
    }
}
