package net.codestory.jajascript.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HelloHttpRequestHandler extends AbstractHandler {

    public static final String SAMPLE_HELLO_RESPONSE = "Hello OneHandler";
    
    private final Logger logger = LoggerFactory.getLogger(HelloHttpRequestHandler.class);
    
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException,
        ServletException {
        if ("/hello".equals(target) && "GET".equals(baseRequest.getMethod())) {
            logger.debug("Received incoming request. Request = {}", baseRequest);
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().append(SAMPLE_HELLO_RESPONSE);
            baseRequest.setHandled(true);
        }
    }
}