/**
 * 
 */
package net.codestory.jajascript.optimizer;

import java.util.ArrayList;
import java.util.List;

import net.codestory.jajascript.domain.RentalWish;

/**
 * @author jmoutsinga
 * 
 */
public class RentalWishesByStartHour {

    private int startHour;
    private List<RentalWish> rentalWishes;

    public RentalWishesByStartHour(int startHour) {
        this.startHour = startHour;
        rentalWishes = new ArrayList<RentalWish>();
    }

    public List<RentalWish> getRentalWishes() {
        return rentalWishes;
    }

    public int getStartHour() {
        return startHour;
    }

    public boolean add(RentalWish rentalWish) {
        if (startHour != rentalWish.getStartHour()) {
            return false;
        }
        rentalWishes.add(rentalWish);
        return true;
    }

    public RentalWish getBestRentalWishByAvgPrice() {
        RentalWish best = null;
        for (RentalWish rentalWish : rentalWishes) {
            if (best == null) {
                best = rentalWish;
                continue;
            }

            if (best.getAveragePrice() < rentalWish.getAveragePrice()) {
                best = rentalWish;
            }
        }
        return best;
    }

    public RentalWish getBestRentalWishByMaxPrice() {

        RentalWish best = null;
        for (RentalWish rentalWish : rentalWishes) {
            if (best == null) {
                best = rentalWish;
                continue;
            }

            if (best.getPrice() < rentalWish.getPrice()) {
                best = rentalWish;
            }
        }
        return best;

    }
}
