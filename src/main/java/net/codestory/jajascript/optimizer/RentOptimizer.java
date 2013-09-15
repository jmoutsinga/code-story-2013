package net.codestory.jajascript.optimizer;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.ui.Timeline;

public interface RentOptimizer {

    public OptimalSpaceshiftPath optimize();

    public Timeline getResultedTimeline();

}