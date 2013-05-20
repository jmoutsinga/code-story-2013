/**
 * 
 */
package net.codestory.jajascript.jettyserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrap the http server
 * 
 * @author Julien Moutsinga (aka jmoutsinga)
 * 
 */
public class JettyServerWrapper implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(JettyServerWrapper.class);

    public static int SERVER_PORT = 8080;

    private Server serverInstance;

    private Thread serverThread;

    public void start() {
        serverThread = new Thread(new JettyServerWrapper());
        serverThread.start();
    }

    public void stop() {
        logger.debug("Stopping server...");
        if (serverInstance != null && !serverInstance.isStopped()) {
            try {
                serverInstance.stop();
                logger.debug("Server stopped...");
            } catch (Throwable e) {
                logger.error("Error while stopping server", e);
            } finally {
                serverInstance = null;
            }
        }
    }

    public void restart() {
        stop();
        start();
    }

    @Override
    public void run() {
        logger.debug("Server starting...");
        if (serverInstance == null) {
            serverInstance = new Server(SERVER_PORT);
            HandlerList handlers = new HandlerList();
            handlers.addHandler(new JajascriptRequestHandler());
            serverInstance.setHandler(handlers);
            try {
                serverInstance.start();
                logger.debug("Listening on port " + SERVER_PORT);
                logger.debug("Server started");
                serverInstance.join();
            } catch (Exception e) {
                logger.error("Unable to start the server", e);
                serverInstance = null;
            }
        }
    }
}
