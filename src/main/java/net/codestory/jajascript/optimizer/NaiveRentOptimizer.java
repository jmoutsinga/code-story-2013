package net.codestory.jajascript.optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import net.codestory.jajascript.Path;
import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaiveRentOptimizer implements RentOptimizer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<RentalWish> rentalWishes;

    public NaiveRentOptimizer(List<RentalWish> rentRequests) {

        this.rentalWishes = rentRequests;
        logger.info("NaiveRentOptimizer initialize with {} flight rent request", rentRequests.size());
    }

    public OptimalSpaceshiftPath optimize() {
        long startTime = System.currentTimeMillis();
        logger.debug("Optimizing...");
        List<Path> paths = new ArrayList<>();

        TreeMap<Long, SortedSet<Path>> priceToPath = new TreeMap<>();
        for (RentalWish rentalWish : rentalWishes) {
            logger.info("Creating path with rental wish request {}", rentalWish);
            Path path = new Path(rentalWish);
            logger.debug("Adding path to price map...");

            // addPathToPriceMap(priceToPath, path);

            List<Path> newPaths = new ArrayList<>();
            for (Path currentPath : paths) {
                Path newPath = currentPath.tryNewPath(rentalWish);
                if (newPath != Path.NULL_PATH) {
                    newPaths.add(newPath);

                    // addPathToPriceMap(priceToPath, newPath);
                }
            }
            paths.add(path);
            logger.debug("Added path to list of path. List of path size = {}", paths.size());
            paths.addAll(newPaths);
            logger.debug("Added {} new path to list of path. List of path size = {}", newPaths.size(), paths.size());
            logger.info("Total computed path = {}", paths.size());
        }

        for (Path path : paths) {
            long totalPrice = path.getTotalPrice();
            SortedSet<Path> set = priceToPath.get(totalPrice);
            if (set == null) {
                set = new TreeSet<>();
                priceToPath.put(totalPrice, set);
            }
            boolean add = set.add(path);
            if (!add) {
                logger.warn("Path was not added to set as already exist.\n Path = {}\n Set = {}", path, set);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Computed paths :");
            for (SortedSet<Path> pathsWithSamePrice : priceToPath.values()) {
                for (Path path2 : pathsWithSamePrice) {
                    logger.debug("Path : {}", path2);
                }
            }
        }

        Path bestPath = priceToPath.lastEntry().getValue().last();
        logger.info("Best path : {}", bestPath);
        logger.info("Optimize took {} ms", System.currentTimeMillis() - startTime);
        return new OptimalSpaceshiftPath(bestPath.getTotalPrice(), bestPath.getCompanies());
    }

    // private void addPathToPriceMap(TreeMap<Long, SortedSet<Path>>
    // priceToPath, Path path) {
    // long totalPrice = path.getTotalPrice();
    // SortedSet<Path> set = priceToPath.get(totalPrice);
    // if (set == null) {
    // set = new TreeSet<>();
    // priceToPath.put(totalPrice, set);
    // }
    // boolean add = set.add(path);
    // if (!add) {
    // logger.warn("Path was not added to set as already exist.\n Path = {}\n Set = {}",
    // path, set);
    // }
    // }
}
