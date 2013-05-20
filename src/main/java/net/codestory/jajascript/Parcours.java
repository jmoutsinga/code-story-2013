package net.codestory.jajascript;

import java.util.ArrayList;
import java.util.List;

import net.codestory.jajascript.domain.RentalWish;

public class Parcours {

    private List<RentalWish> vols = new ArrayList<RentalWish>();

    public Parcours(RentalWish rentalWish) {
        this.vols.add(rentalWish);
    }

    public void add(RentalWish rentalWish) {
        vols.add(rentalWish);
    }

    public long getGainTotal() {
        long result = 0;
        for (RentalWish vol : vols) {
            result += vol.getPrice();
        }
        return result;
    }

    public List<String> getVolsName() {
        List<String> result = new ArrayList<>();
        for (RentalWish vol : vols) {
            result.add(vol.getCompanyName());
        }
        return result;
    }

}
