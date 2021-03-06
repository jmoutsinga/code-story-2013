package net.codestory.jajascript;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.util.JsonHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class JajascriptServlet
 */
@WebServlet("/optimize")
public class JajascriptServlet extends HttpServlet {

    private static final long serialVersionUID = -8018062926565055536L;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JajascriptServlet() {
        super();
    }

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

        JajascriptHttpRequestHandler requestHandler = new JajascriptHttpRequestHandler();
        OptimalSpaceshiftPath result = requestHandler.handle(request.getInputStream());

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        JsonHelper helper = new JsonHelper();
        response.getWriter().append(helper.toJsonString(result));
    }
}