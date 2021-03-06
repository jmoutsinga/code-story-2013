/**
 * 
 */
package net.codestory.jajascript;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codestory.jajascript.ui.Timeline;
import net.codestory.jajascript.ui.chronoline.ChronolineAdapter;
import net.codestory.jajascript.ui.chronoline.ChronolineData;
import net.codestory.jajascript.util.JsonHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmoutsinga
 * 
 */
@WebServlet("/findOptimalPath")
public class FindOptimalPathServlet extends HttpServlet {
    private static final long serialVersionUID = -6159132766875052109L;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new ServletException("GET request not handle by server");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        // Debug.debug(request);
        String fileContent = request.getParameter("fileContent");

        TimelineRequestHandler requestHandler = new TimelineRequestHandler();
        Timeline timeline = requestHandler.handle(new ByteArrayInputStream(fileContent.getBytes()));
        ChronolineAdapter adapter = new ChronolineAdapter(timeline.getBegin());
        ChronolineData data = adapter.toChronolineData(timeline);
        JsonHelper jsonHelper = new JsonHelper();
        String jsonString = jsonHelper.toJsonString(data);
        logger.debug("json result = {}", jsonString);
        response.getOutputStream().write(jsonString.getBytes());
    }

}
