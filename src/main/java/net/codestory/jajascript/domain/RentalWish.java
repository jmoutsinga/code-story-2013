/**
 * 
 */
package net.codestory.jajascript.domain;

/**
 * @author jmoutsinga
 * 
 */
public class RentalWish {

    private String VOL;
    private int DEPART;
    private int DUREE;
    private int PRIX;

    private int endHour = -1;
    private Float averageHourPrice;

    public RentalWish() {

    }

    public String getCompanyName() {
        return VOL;
    }

    public void setCompanyName(String companyName) {
        this.VOL = companyName;
    }

    public int getStartHour() {
        return DEPART;
    }

    public void setStartHour(int startHour) {
        this.DEPART = startHour;
    }

    public int getRentLength() {
        return DUREE;
    }

    public void setRentLength(int rentLength) {
        this.DUREE = rentLength;
    }

    public int getPrice() {
        return PRIX;
    }

    public void setPrice(int price) {
        this.PRIX = price;
    }

    public int getEndHour() {
        if (endHour == -1) {
            endHour = DEPART + DUREE;
        }
        return endHour;
    }

    public Float getAveragePrice() {
        if (averageHourPrice == null) {
            averageHourPrice = Float.valueOf(PRIX / DUREE);
        }
        return averageHourPrice;
    }

    public Period getPeriod() {
        return new Period(getStartHour(), getEndHour());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RentRequest [vol=").append(VOL).append(", depart=").append(DEPART).append(", duree=").append(DUREE)
            .append(", prix=").append(PRIX).append("]");
        return builder.toString();
    }

    public boolean isBefore(RentalWish rentRequest) {
        return getEndHour() <= rentRequest.getStartHour();
    }

    public boolean isAfter(RentalWish rentRequest) {
        return getStartHour() >= rentRequest.getEndHour();
    }

    public int getDuration() {
        return DUREE;
    }
}