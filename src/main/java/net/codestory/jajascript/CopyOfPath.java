package net.codestory.jajascript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import net.codestory.jajascript.domain.RentalWish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyOfPath implements Comparable<CopyOfPath> {

    public static final CopyOfPath NULL_PATH = new CopyOfPath();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String pathId;

    private long totalPrice;

    private long pathLength;

    private TreeMap<Integer, RentalWish> vols;

    private CopyOfPath() {
        pathId = "";
        vols = new TreeMap<Integer, RentalWish>();
    }

    public CopyOfPath(RentalWish rentRequest) {
        totalPrice = rentRequest.getPrice();
        vols = new TreeMap<Integer, RentalWish>();
        vols.put(rentRequest.getStartHour(), rentRequest);
        pathLength = rentRequest.getRentLength();
        pathId = rentRequest.getCompanyName();
    }

    public CopyOfPath(List<RentalWish> rentRequests) {
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

    public CopyOfPath tryNewPath(RentalWish rentRequest) {
        logger.debug("Trying new path with rent request {} to path {}", rentRequest, this);

        if (this == NULL_PATH) {
            return NULL_PATH;
        }

        boolean newPathFound = false;
        List<RentalWish> values = new ArrayList<>(vols.values());

        for (int i = 0; i < values.size(); i++) {
            RentalWish currentRequest = values.get(i);
            RentalWish nextRequest = i + 1 == values.size() ? null : values.get(i + 1);
            if (rentRequest.isBefore(currentRequest)) {
                logger.debug("Current request {} is before actual rent {}", rentRequest, currentRequest);
                newPathFound = true;
                break;
            }

            if (rentRequest.isAfter(currentRequest)) {
                if (nextRequest == null || rentRequest.isBefore(nextRequest)) {
                    logger.debug("Current request {} is after actual rent {} and before next rent {}", new Object[] { rentRequest,
                        currentRequest, nextRequest });
                    newPathFound = true;
                    break;
                }
            }
        }
        if (newPathFound) {
            List<RentalWish> newPath = new ArrayList<>(vols.values());
            newPath.add(rentRequest);
            CopyOfPath path = new CopyOfPath(newPath);
            logger.info("New possible path found. New path = {}", path);
            return path;
        } else {
            logger.debug("No new path found. Returning null path");
            return CopyOfPath.NULL_PATH;
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
        CopyOfPath other = (CopyOfPath) obj;
        return pathId.equals(other.pathId);
    }

    @Override
    public int compareTo(CopyOfPath path) {
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
