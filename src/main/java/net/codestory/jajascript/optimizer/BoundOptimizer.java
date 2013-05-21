package net.codestory.jajascript.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoundOptimizer implements RentOptimizer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentalWishes;

    public BoundOptimizer(List<RentalWish> rentalWishes) {
        this.rentalWishes = rentalWishes;
    }

    @Override
    public OptimalSpaceshiftPath optimize() {
        // Organize by startHour
        Map<Integer, RentalWishesByStartHour> byStartHour = new TreeMap<Integer, RentalWishesByStartHour>();
        for (RentalWish rentalWish : rentalWishes) {
            int startHour = rentalWish.getStartHour();
            RentalWishesByStartHour wishesByStartHour = byStartHour.get(startHour);
            if (wishesByStartHour == null) {
                wishesByStartHour = new RentalWishesByStartHour(startHour);
            }
            wishesByStartHour.add(rentalWish);
            byStartHour.put(startHour, wishesByStartHour);
        }

        // Compute paths by bounding from rentalWishes to rentalWishes

        Set<Integer> startHours = byStartHour.keySet();
        Path optimalPath = null;
        for (Integer startHour : startHours) {
            RentalWishesByStartHour wishesByStartHour = byStartHour.get(startHour);
            List<RentalWish> currents = wishesByStartHour.getRentalWishes();
            for (RentalWish rentalWish : currents) {
                Path path = new Path(rentalWish);
                List<Path> possiblePaths = computePossiblePaths(path, byStartHour);
                optimalPath = selectOptimalPath(optimalPath, possiblePaths);
            }
        }

        logger.info("Optimal path : {}", optimalPath);

        return new OptimalSpaceshiftPath(optimalPath.getGainTotal(), optimalPath.getPathNames());
    }

    private Path selectOptimalPath(Path optimalPath, List<Path> possiblePaths) {
        for (Path possiblePath : possiblePaths) {
            if (optimalPath == null) {
                optimalPath = possiblePath;
            }

            if (optimalPath.getGainTotal() < possiblePath.getGainTotal()) {
                optimalPath = possiblePath;
            }
        }
        return optimalPath;
    }

    private List<Path> computePossiblePaths(Path path, Map<Integer, RentalWishesByStartHour> byStartHour) {
        List<Path> result = new ArrayList<Path>();
        Integer nextStartHour = path.getEndHour();
        RentalWishesByStartHour nextByStartHour = byStartHour.get(nextStartHour);
        if (nextByStartHour == null) {
            result.add(path);
        } else {
            List<RentalWish> wishes = nextByStartHour.getRentalWishes();
            for (RentalWish rentalWish : wishes) {
                Path newPath = path.continuePath(rentalWish);
                List<Path> possiblePaths = computePossiblePaths(newPath, byStartHour);
                result.addAll(possiblePaths);
            }
        }
        return result;
    }

}
