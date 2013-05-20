/**
 * 
 */
package net.codestory.jajascript;

import java.util.ArrayList;
import java.util.List;

import net.codestory.jajascript.domain.RentalWish;

/**
 * @author jmoutsinga
 * 
 */
public class BestAveragePrice {

    private int startHour;
    private List<RentalWish> vols;

    public BestAveragePrice(int startHour) {
        this.startHour = startHour;
        vols = new ArrayList<RentalWish>();
    }

    public int getStartHour() {
        return startHour;
    }

    public void add(RentalWish rentalWish) {
        vols.add(rentalWish);
    }

    public RentalWish getBestAvgPriceRentalWish() {
        RentalWish best = null;
        for (RentalWish rentalWish : vols) {
            if (best == null) {
                best = rentalWish;
                continue;
            }

            if (best.getAveragePrice() < rentalWish.getAveragePrice()) {
                best = rentalWish;
            }

            if (best.getPrice() < rentalWish.getPrice()) {
                best = rentalWish;
            }
        }
        return best;
    }

}
