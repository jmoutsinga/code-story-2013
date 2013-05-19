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
import net.codestory.jajascript.optimizer.NaiveRentOptimizer;
import net.codestory.jajascript.optimizer.RentOptimizer;
import net.codestory.jajascript.util.JsonHelper;

/**
 * Servlet implementation class JajascriptServlet
 */
@WebServlet("/optimize")
public class JajascriptServlet extends HttpServlet {

    private static final long serialVersionUID = -8018062926565055536L;

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

        RentOptimizer rentOptimizer = new NaiveRentOptimizer(bestRentalWishes);
        OptimalSpaceshiftPath result = rentOptimizer.optimize();

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(helper.toJsonString(result));
    }
}