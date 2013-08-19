/**
 * 
 */
package net.codestory.jajascript.ui;

/**
 * @author jmoutsinga
 * 
 */
public class Index implements Comparable<Index> {

    private int position;

    public Index(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + position;
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
        Index other = (Index) obj;
        if (position != other.position)
            return false;
        return true;
    }

    @Override
    public int compareTo(Index o) {
        if (this.position < o.position) {
            return -1;
        } else if (this.position > o.position) {
            return 1;
        }
        return 0;
    }

}
