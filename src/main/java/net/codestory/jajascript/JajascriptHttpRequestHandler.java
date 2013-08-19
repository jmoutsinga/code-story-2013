/**
 * 
 */
package net.codestory.jajascript;

import java.io.InputStream;
import java.util.List;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.filter.RentalWishFilterByPeriod;
import net.codestory.jajascript.optimizer.BoundOptimizer;
import net.codestory.jajascript.optimizer.RentOptimizer;
import net.codestory.jajascript.util.JsonHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class JajascriptHttpRequestHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OptimalSpaceshiftPath handle(InputStream inputStream) {
        JsonHelper helper = new JsonHelper();
        List<RentalWish> rentalWishes = helper.fromJsonAsStream(inputStream);

        List<RentalWish> bestRentalWishes = new RentalWishFilterByPeriod(rentalWishes).doFilter();

        RentOptimizer rentOptimizer = new BoundOptimizer(bestRentalWishes);
        long now = System.currentTimeMillis();
        OptimalSpaceshiftPath result = rentOptimizer.optimize();
        logger.info("Optimizer took {} ms", System.currentTimeMillis() - now);
        return result;
    }

}
