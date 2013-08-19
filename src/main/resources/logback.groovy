import java.util.logging.Logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.core.ConsoleAppender;


appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "[%date{HH:mm:ss.SSS}][%-5level][%thread][%30logger{30}] %msg%n"
    }
}

def bySecond = timestamp("yyyy-MM-dd_HH'h'mm'm'ss's'")

appender("FILE", FileAppender) {
  file = "target/log-${bySecond}.log"
  encoder(PatternLayoutEncoder) {
    pattern = "[%date{HH:mm:ss.SSS}][%-5level][%thread][%30logger{30}] %msg%n"
  }
}

root(Level.INFO, ["CONSOLE"])
//root(Level.INFO, ["FILE", "CONSOLE"])
//root(Level.INFO, ["FILE"])

logger("net.codestory.jajascript", Level.DEBUG)
logger("net.codestory.jajascript.Path", Level.DEBUG)
logger("net.codestory.jajascript.ui", Level.INFO)
logger("net.codestory.jajascript.optimizer", Level.DEBUG)
logger("net.codestory.jajascript.BestPathFinder", Level.DEBUG)
