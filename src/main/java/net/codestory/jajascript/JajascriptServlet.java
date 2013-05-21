package net.codestory.jajascript;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codestory.jajascript.domain.OptimalSpaceshiftPath;
import net.codestory.jajascript.domain.RentalWish;
import net.codestory.jajascript.filter.RentalWishFilterByPeriod;
import net.codestory.jajascript.optimizer.BoundOptimizer;
import net.codestory.jajascript.optimizer.RentOptimizer;
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

        JsonHelper helper = new JsonHelper();
        List<RentalWish> rentalWishes = helper.fromJsonAsStream(request.getInputStream());

        List<RentalWish> bestRentalWishes = new RentalWishFilterByPeriod(rentalWishes).doFilter();

        RentOptimizer rentOptimizer = new BoundOptimizer(bestRentalWishes);
        long now = System.currentTimeMillis();
        OptimalSpaceshiftPath result = rentOptimizer.optimize();
        logger.info("Optimizer took {} ms", System.currentTimeMillis() - now);

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(helper.toJsonString(result));
    }
}