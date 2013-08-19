package net.codestory.jajascript.ui;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.codestory.jajascript.domain.Path;
import net.codestory.jajascript.domain.RentalWish;

public class AsciiRepresentation implements Observer {

    private static final String MARKER = "|";
    private static final String MARKER_SPACER = createMarkerSpacer();

    private static final String SPACER_CHAR = "_";
    private static final int SPACER_LENGHT = 5;

    private static String createMarkerSpacer() {
        StringBuilder sb = new StringBuilder(MARKER);
        for (int i = 1; i < SPACER_LENGHT; i++) {
            sb.append(SPACER_CHAR);
        }
        return sb.toString();
    }

    private final PrintStream out;
    private final Timeline timeline;

    public AsciiRepresentation(PrintStream out, Timeline timeline) {
        this.out = out;
        this.timeline = timeline;
        printHeader();
    }

    public void printHeader() {
        out.println("Total Rental Wishes : " + timeline.getTotalRentalWishes());
        print();

    }

    private void print() {
        for (int i = timeline.getBegin(); i < timeline.getEnd(); i++) {
            String indexStr = "" + i;
            while (indexStr.length() < SPACER_LENGHT) {
                indexStr += SPACER_CHAR;
            }
            out.print(indexStr);
        }
        out.println("");
    }

    public void printPath(Path path) {
        List<RentalWish> flights = path.getFlights();
        Iterator<RentalWish> iterator = flights.iterator();
        RentalWish flight = iterator.next();

        printFlight(flight);
        while (iterator.hasNext()) {
            printFlight(iterator.next());
        }
    }

    private void printFlight(RentalWish flight) {
        int startHour = flight.getStartHour();
        String flightDetails = "[" + startHour + " " + flight.getCompanyName() + " * " + flight.getPrice() + " *";
        for (int i = flightDetails.length(); i < (SPACER_LENGHT * flight.getDuration() - 1); i++) {
            flightDetails += "=";
        }
        flightDetails += "]";
        for (int i = timeline.getBegin(); i <= timeline.getEnd(); i++) {
            if (i == startHour) {
                while (flightDetails.length() % SPACER_LENGHT != 0) {
                    flightDetails += SPACER_CHAR;
                }
                out.print(flightDetails);
                i = i + (flightDetails.length() / SPACER_LENGHT);
            } else {
                out.print(MARKER_SPACER);
            }
        }
        out.println("");
    }

    // @Override
    // public void update(Observable o, Object arg) {
    // Path path = (Path) arg;
    // printPath(path);
    // }

    @Override
    public void update(Observable o, Object arg) {
        RentalWish rentalWish = (RentalWish) arg;
        printFlight(rentalWish);
    }

}
