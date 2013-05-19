/**
 * 
 */
package net.codestory.jajascript;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;

import net.codestory.jajascript.domain.Period;
import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class BestPathFinder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NavigableMap<Period, RentalWish> periodRentRequestMap;
    private RentalWish currentRentRequest;
    private Path bestPath;

    public BestPathFinder(NavigableMap<Period, RentalWish> periodRentRequestMap, RentalWish currentRentRequest, Path bestPath) {
        this.periodRentRequestMap = periodRentRequestMap;
        this.currentRentRequest = currentRentRequest;
        this.bestPath = bestPath;
    }

    public Path doFind() {

        Set<Entry<Period, RentalWish>> entrySet = periodRentRequestMap.entrySet();

        for (Entry<Period, RentalWish> entry : entrySet) {
            RentalWish nextRequest = entry.getValue();
            if (nextRequest.compareTo(currentRentRequest) <= 0) {
                logger.debug("Skiping rent request {}, current {}", nextRequest, currentRentRequest);
                continue;
            }

            Path betterPath = bestPath.tryNewPath(nextRequest);
            if (Path.NULL_PATH != betterPath) {
                logger.debug("Better path found [{}]. Trying with highers rent request ", betterPath);
                boolean includeCurrentValue = false;
                NavigableMap<Period, RentalWish> greatersValues = periodRentRequestMap.tailMap(entry.getKey(), includeCurrentValue);

                BestPathFinder finder = new BestPathFinder(greatersValues, nextRequest, betterPath);
                Path bestPathFound = finder.doFind();
                logger.debug("Best path found with highers values...");
                if (bestPathFound != null && bestPathFound.compareTo(bestPath) > 0) {
                    logger.debug("Price is best, keeping new");
                    bestPath = bestPathFound;
                    break;
                }
            }
        }
        return bestPath;
    }
}
