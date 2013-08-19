package net.codestory.jajascript;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/readInputFile")
@MultipartConfig
public class JajascriptUIServlet extends HttpServlet {

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

        final Part filePart = request.getPart("sendFile");

        byte[] buffer = new byte[1024];
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = filePart.getInputStream();
        while (inputStream.read(buffer) != -1) {
            outputStream.write(buffer);
        }
        outputStream.flush();
        outputStream.close();
    }
}