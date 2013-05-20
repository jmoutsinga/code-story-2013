/**
 * 
 */
package net.codestory.jajascript.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codestory.jajascript.JajascriptServlet;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class JajascriptRequestHandler extends AbstractHandler {

    private static final String CONTEXT = "/jajascript/optimize";

    private final Logger logger = LoggerFactory.getLogger(JajascriptRequestHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
     * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException,
        ServletException {
        logger.debug("Received incoming request. Request = {}", baseRequest);
        if (CONTEXT.equals(target)) {

            JajascriptServlet requestHandler = new JajascriptServlet();
            switch (baseRequest.getMethod()) {
            case "POST": {
                requestHandler.doPost(request, response);
                break;
            }
            case "GET": {
                requestHandler.doGet(request, response);
                break;
            }
            default: {
                throw new ServletException("HTTP Method not supported");
            }
            }
            baseRequest.setHandled(true);
        }
    }
}
