/**
 * 
 */
package net.codestory.jajascript.domain;

/**
 * @author jmoutsinga
 * 
 */
public class Period implements Comparable<Period> {

    private final int startHour;
    private final int endHour;

    public Period(int startHour, int endHour) {
        if (startHour >= endHour) {
            throw new IllegalArgumentException("Cannot create period with values startHour=" + startHour + ", endHour=" + endHour);
        }
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endHour;
        result = prime * result + startHour;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Period other = (Period) obj;
        if (endHour != other.endHour)
            return false;
        if (startHour != other.startHour)
            return false;
        return true;
    }

    @Override
    public int compareTo(Period other) {
        if (startHour < other.startHour) {
            return -1;
        } else if (startHour > other.startHour) {
            return 1;
        } else {
            if (endHour < other.endHour) {
                return -1;
            } else if (endHour > other.endHour) {
                return 1;
            }
            return 0;
        }
    }
}
