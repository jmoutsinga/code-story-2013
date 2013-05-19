package net.codestory.jajascript.optimizer;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.codestory.jajascript.Path;
import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.filter.StartHourFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecursiveRentOptimizer2 implements RentOptimizer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentRequests;

    public RecursiveRentOptimizer2(List<RentalWish> rentRequests) {

        this.rentRequests = rentRequests;
        logger.info("Optimizer initialize with {} flight rent request", rentRequests.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.codestory.jajascript.RentOptimizer#optimize()
     */
    @Override
    public OptimalSpaceshiftPath optimize() {
        long startTime = System.currentTimeMillis();
        logger.debug("Optimizing...");

        // Filtering request to keep only best offers

        StartHourFilter filter = new StartHourFilter(rentRequests);
        TreeMap<Integer, RentalWish> bestRentRequestByPeriod = filter.doFilter();
        Set<Integer> keySet = bestRentRequestByPeriod.keySet();

        Iterator<Integer> iterator = keySet.iterator();
        SortedSet<Path> allPaths = new TreeSet<>();
        while (iterator.hasNext()) {
            Integer current = iterator.next();

            // take the first (in the period order)
            RentalWish currentRentRequest = bestRentRequestByPeriod.get(current);

            // create a path, default best path is the request alone
            Path bestPath = new Path(currentRentRequest);

            allPaths.add(findBestPath(bestRentRequestByPeriod, currentRentRequest, bestPath));

            // best paths computed, removing the request from the map
            iterator.remove();
        }

        Path bestPath = allPaths.last();
        logger.info("Best path : {}", bestPath);
        logger.info("Optimize took {} ms", System.currentTimeMillis() - startTime);
        return new OptimalSpaceshiftPath(bestPath.getTotalPrice(), bestPath.getCompanies());
    }

    private Path findBestPath(NavigableMap<Integer, RentalWish> periodRentRequestMap, RentalWish currentRentRequest, Path bestPath) {

        Set<Entry<Integer, RentalWish>> entrySet = periodRentRequestMap.entrySet();

        for (Entry<Integer, RentalWish> entry : entrySet) {
            RentalWish nextRequest = entry.getValue();
            if (nextRequest == currentRentRequest) {
                continue;
            }

            Path path = bestPath.tryNewPath(nextRequest);
            if (path != null) {
                boolean includeCurrentValue = false;
                NavigableMap<Integer, RentalWish> greatersValues = periodRentRequestMap.tailMap(entry.getKey(), includeCurrentValue);

                Path bestPathFound = findBestPath(greatersValues, nextRequest, path);
                if (Path.NULL_PATH != bestPathFound && path.compareTo(bestPath) > 0) {
                    bestPath = path;
                }

            }

        }
        return bestPath;
    }
}
