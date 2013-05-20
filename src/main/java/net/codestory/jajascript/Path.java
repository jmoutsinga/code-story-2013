package net.codestory.jajascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Path implements Comparable<Path> {

    public static final Path NULL_PATH = new Path();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String pathId;

    private long totalPrice;

    private long pathLength;

    private TreeMap<Integer, RentalWish> vols;

    private Path() {
        pathId = "";
        vols = new TreeMap<Integer, RentalWish>();
    }

    public Path(RentalWish rentRequest) {
        totalPrice = rentRequest.getPrice();
        vols = new TreeMap<Integer, RentalWish>();
        vols.put(rentRequest.getStartHour(), rentRequest);
        pathLength = rentRequest.getRentLength();
        pathId = rentRequest.getCompanyName();
    }

    public Path(List<RentalWish> rentRequests) {
        vols = new TreeMap<Integer, RentalWish>();
        StringBuilder sb = new StringBuilder(50 * rentRequests.size());
        for (RentalWish rentRequest : rentRequests) {
            sb.append(rentRequest.getCompanyName());
            totalPrice += rentRequest.getPrice();
            vols.put(rentRequest.getStartHour(), rentRequest);
            pathLength += rentRequest.getRentLength();
        }
        pathId = sb.toString();
    }

    public Path tryNewPath(RentalWish rentRequest) {
        logger.debug("Trying new path with rent request {} to path {}", rentRequest, this);

        if (this == NULL_PATH) {
            return NULL_PATH;
        }

        RentalWish currentRequest = vols.lastEntry().getValue();

        if (rentRequest.isAfter(currentRequest)) {
            logger.debug("Current request {} is after last rent {}", rentRequest, currentRequest);
            List<RentalWish> newPath = new ArrayList<>(vols.values());
            newPath.add(rentRequest);
            Path path = new Path(newPath);
            logger.info("New possible path found. New path = {}", path);
            return path;
        } else {
            logger.info("No new path found. Returning null path");
            return Path.NULL_PATH;
        }
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public long getPathLength() {
        return pathLength;
    }

    public int getNbVols() {
        return vols.size();
    }

    @Override
    public String toString() {
        return new StringBuilder(256).append("pathId=").append(pathId)//
            .append(", totalPrice=").append(totalPrice)//
            .append(", vols=").append(getCompanies()).toString();
    }

    public List<String> getCompanies() {
        if (vols == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>(vols.size());
        for (RentalWish rentRequest : vols.values()) {
            result.add(rentRequest.getCompanyName());
        }
        return result;
    }

    public int getEndHour() {
        return vols.lastEntry().getValue().getEndHour();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pathId == null) ? 0 : pathId.hashCode());
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
        Path other = (Path) obj;
        return pathId.equals(other.pathId);
    }

    @Override
    public int compareTo(Path path) {
        if (getTotalPrice() < path.getTotalPrice()) {
            return -1;
        } else if (getTotalPrice() > path.getTotalPrice()) {
            return 1;
        } else {
            if (getPathLength() < path.getPathLength()) {
                return -1;
            } else if (getPathLength() > path.getPathLength()) {
                return 1;
            } else {
                if (getNbVols() < path.getNbVols()) {
                    return -1;
                } else if (getNbVols() > path.getNbVols()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
