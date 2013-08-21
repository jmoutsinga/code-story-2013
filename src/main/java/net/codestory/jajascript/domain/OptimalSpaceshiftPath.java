/**
 * 
 */
package net.codestory.jajascript.domain;

import java.util.Collections;
import java.util.List;

/**
 * @author jmoutsinga
 * 
 */
public class OptimalSpaceshiftPath {

    public static final OptimalSpaceshiftPath NULL = new OptimalSpaceshiftPath(0, Collections.<String> emptyList());
    private final long gain;
    private final List<String> path;

    public OptimalSpaceshiftPath(long optimalGain, List<String> companies) {
        gain = optimalGain;
        path = companies;
    }

    public long getGain() {
        return gain;
    }

    public List<String> getVols() {
        return path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (gain ^ (gain >>> 32));
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        OptimalSpaceshiftPath other = (OptimalSpaceshiftPath) obj;
        if (gain != other.gain)
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.toString().equals(other.path.toString()))
            return false;
        return true;
    }

}
