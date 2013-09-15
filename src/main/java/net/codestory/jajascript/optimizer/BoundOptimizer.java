package net.codestory.jajascript.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.Path;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.ui.AsciiRepresentation;
import net.codestory.jajascript.ui.Timeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoundOptimizer implements RentOptimizer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentalWishes;
    private Timeline timeline;

    public BoundOptimizer(List<RentalWish> rentalWishes) {
        this.rentalWishes = rentalWishes;
        timeline = createTimeLine();
    }

    private Timeline createTimeLine() {
        int minStartHour = -1;
        int maxEndHour = -1;
        for (RentalWish rentalWish : rentalWishes) {
            int startHour = rentalWish.getStartHour();
            if (minStartHour == -1 || minStartHour > startHour) {
                minStartHour = startHour;
            }
            int endHour = rentalWish.getEndHour();
            if (maxEndHour == -1 || maxEndHour < endHour) {
                maxEndHour = endHour;
            }
        }
        return new Timeline(rentalWishes.size(), minStartHour, maxEndHour);
    }

    @Override
    public OptimalSpaceshiftPath optimize() {

        if (rentalWishes.isEmpty()) {
            return OptimalSpaceshiftPath.NULL;
        }

        // Organize by startHour
        TreeMap<Integer, RentalWishesByStartHour> byStartHour = new TreeMap<Integer, RentalWishesByStartHour>();
        for (RentalWish rentalWish : rentalWishes) {
            int startHour = rentalWish.getStartHour();

            RentalWishesByStartHour wishesByStartHour = byStartHour.get(startHour);
            if (wishesByStartHour == null) {
                wishesByStartHour = new RentalWishesByStartHour(startHour);
            }
            wishesByStartHour.add(rentalWish);
            byStartHour.put(startHour, wishesByStartHour);
        }

        AsciiRepresentation concreteUi = new AsciiRepresentation(System.err, timeline);
        timeline.addObserver(concreteUi);

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
            timeline.addPath(path);
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

    @Override
    public Timeline getResultedTimeline() {
        return timeline;
    }

}
