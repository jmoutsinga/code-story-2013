package net.codestory.jajascript.optimizer;

import java.util.ArrayList;
import java.util.List;

import net.codestory.jajascript.domain.RentalWish;

public class Path {

    private List<RentalWish> flights;
    private Long gainTotal;
    private List<String> pathNames;

    public Path(RentalWish rentalWish) {
        flights = new ArrayList<>();
        flights.add(rentalWish);
    }

    public Path(List<RentalWish> flights) {
        this.flights = new ArrayList<>(flights);
    }

    public Long getGainTotal() {
        if (gainTotal == null) {
            computePathNamesAndGainTotal();
        }
        return gainTotal;
    }

    public List<String> getPathNames() {
        if (pathNames == null) {
            computePathNamesAndGainTotal();
        }
        return pathNames;
    }

    private void computePathNamesAndGainTotal() {
        gainTotal = Long.valueOf(0l);
        pathNames = new ArrayList<>(flights.size());
        for (RentalWish rentalWish : flights) {
            gainTotal += rentalWish.getPrice();
            pathNames.add(rentalWish.getCompanyName());
        }
    }

    public Path continuePath(RentalWish rentalWish) {
        Path path = new Path(flights);
        path.addFlight(rentalWish);
        return path;
    }

    private void addFlight(RentalWish rentalWish) {
        flights.add(rentalWish);
    }

    public Integer getEndHour() {
        return flights.get(flights.size() - 1).getEndHour();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path : \n");
        for (int i = 0; i < flights.size(); i++) {
            RentalWish rentalWish = flights.get(i);
            sb.append("  Vol").append(i + 1).append(" : ").append(rentalWish.getCompanyName()).append("[")
                .append(rentalWish.getStartHour()).append(",").append(rentalWish.getEndHour()).append("]").append("->")
                .append(rentalWish.getPrice()).append("\n");
        }
        sb.append("Total = ").append(getGainTotal());
        return sb.toString();
    }
}