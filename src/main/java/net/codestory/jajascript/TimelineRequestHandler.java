package net.codestory.jajascript;

import java.io.ByteArrayInputStream;
import java.util.List;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.filter.RentalWishFilterByPeriod;
import net.codestory.jajascript.optimizer.BoundOptimizer;
import net.codestory.jajascript.optimizer.RentOptimizer;
import net.codestory.jajascript.ui.Timeline;
import net.codestory.jajascript.util.JsonHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimelineRequestHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TimelineRequestHandler() {
    }

    public Timeline handle(ByteArrayInputStream stream) {
        JsonHelper helper = new JsonHelper();
        List<RentalWish> rentalWishes = helper.fromJsonAsStream(stream);

        List<RentalWish> bestRentalWishes = new RentalWishFilterByPeriod(rentalWishes).doFilter();

        RentOptimizer rentOptimizer = new BoundOptimizer(bestRentalWishes);
        long now = System.currentTimeMillis();
        OptimalSpaceshiftPath result = rentOptimizer.optimize();
        logger.info("Optimizer took {} ms", System.currentTimeMillis() - now);
        logger.info("Optimal choice :\n{}", result);
        return rentOptimizer.getResultedTimeline();
    }
}
