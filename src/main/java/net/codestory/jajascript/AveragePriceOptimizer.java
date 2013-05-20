package net.codestory.jajascript;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.optimizer.RentOptimizer;

public class AveragePriceOptimizer implements RentOptimizer {

    private List<RentalWish> bestRentalWishes;

    public AveragePriceOptimizer(List<RentalWish> bestRentalWishes) {
        this.bestRentalWishes = bestRentalWishes;
    }

    @Override
    public OptimalSpaceshiftPath optimize() {

        TreeMap<Integer, BestAveragePrice> bestAvgPriceByStart = new TreeMap<Integer, BestAveragePrice>();
        for (RentalWish rentalWish : bestRentalWishes) {
            int startHour = rentalWish.getStartHour();
            BestAveragePrice bestAveragePrice = bestAvgPriceByStart.get(startHour);
            if (bestAveragePrice == null) {
                bestAveragePrice = new BestAveragePrice(startHour);
            }
            bestAveragePrice.add(rentalWish);
            bestAvgPriceByStart.put(startHour, bestAveragePrice);
        }

        List<Parcours> results = new ArrayList<Parcours>();

        Set<Integer> keySet = bestAvgPriceByStart.keySet();

        for (Integer startHour : keySet) {
            Parcours parcours = computeParcoursOptimal(startHour, bestAvgPriceByStart);
            results.add(parcours);
        }

        Parcours bestParcours = null;
        for (Parcours parcours : results) {
            if (bestParcours == null) {
                bestParcours = parcours;
                continue;
            }

            if (bestParcours.getGainTotal() < parcours.getGainTotal()) {
                bestParcours = parcours;
            }
        }

        return new OptimalSpaceshiftPath(bestParcours.getGainTotal(), bestParcours.getVolsName());
    }

    private Parcours computeParcoursOptimal(Integer startHour, TreeMap<Integer, BestAveragePrice> bestAvgPriceByStart) {

        BestAveragePrice bestAvgPrice = bestAvgPriceByStart.get(startHour);
        RentalWish firstRentalWish = bestAvgPrice.getBestAvgPriceRentalWish();
        Parcours bestParcours = new Parcours(firstRentalWish);

        RentalWish nextRentalWish = firstRentalWish;
        BestAveragePrice nextAveragePrice = bestAvgPrice;
        while (nextAveragePrice != null) {
            BestAveragePrice bestAveragePrice = bestAvgPriceByStart.get(nextRentalWish.getEndHour());
            if (bestAveragePrice != null) {
                RentalWish rentalWish = bestAveragePrice.getBestAvgPriceRentalWish();
                bestParcours.add(rentalWish);
                nextRentalWish = rentalWish;
                nextAveragePrice = bestAveragePrice;
            } else {
                nextAveragePrice = null;
            }
        }
        return bestParcours;
    }
}
