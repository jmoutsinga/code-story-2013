/**
 * 
 */
package net.codestory.jajascript.ui;

/**
 * @author jmoutsinga
 * 
 */
public class StartIndex implements Comparable<StartIndex> {

    private final int startIndex;

    public StartIndex(int startHour) {
        this.startIndex = startHour;
    }

    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + startIndex;
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
        StartIndex other = (StartIndex) obj;
        if (startIndex != other.startIndex)
            return false;
        return true;
    }

    @Override
    public int compareTo(StartIndex o) {
        if (this.startIndex < o.startIndex) {
            return -1;
        } else if (this.startIndex > o.startIndex) {
            return 1;
        }
        return 0;
    }

}
