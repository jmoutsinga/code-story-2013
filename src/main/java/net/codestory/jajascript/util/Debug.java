/**
 * 
 */
package net.codestory.jajascript.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
public class Debug {

    private final static Logger logger = LoggerFactory.getLogger(Debug.class);

    public static void debug(HttpServletRequest request) throws IOException, ServletException {
        Enumeration<String> attributeNames = request.getAttributeNames();
        int index = 0;
        while (attributeNames.hasMoreElements()) {
            logger.debug("attribute {} = {}", index++, attributeNames.nextElement());
        }

        Enumeration<String> headerNames = request.getHeaderNames();

        index = 0;
        while (headerNames.hasMoreElements()) {
            logger.debug("headerName {} = {}", index++, headerNames.nextElement());

        }

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            logger.debug("paramName : {} = {}", paramName, request.getParameter(paramName));
        }

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            logger.debug("part name {}", part.getName());
            logger.debug("part content type {}", part.getContentType());
            logger.debug("part lenght {}", part.getSize());
        }

    }

}
