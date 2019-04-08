package it.polimi.tiw.messages.controllers;

import it.polimi.tiw.messages.dao.MessageDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/CreateMessage"})
public class CreateMessage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public CreateMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("dbDriver");
            String url = context.getInitParameter("dbUrl");
            String user = context.getInitParameter("dbUser");
            String password = context.getInitParameter("dbPassword");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new UnavailableException("Can't load database driver");
        } catch (SQLException e) {
            throw new UnavailableException("Couldn't get db connection");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String t = request.getParameter("title");
        String b = request.getParameter("body");
        String ds = request.getParameter("date");
        String tids = request.getParameter("topicid");
        String tname = request.getParameter("topicname");

        if (t == null || b == null || ds == null || tids == null) {
            response.sendError(505, "Parameters incomplete");
            return;
        }

        MessageDAO mDAO = new MessageDAO(connection);
        int tid = 0;
        Date d = null;
        String error = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(ds);

        } catch (ParseException e1) {
            error = "Bad date format creation";
            // throw new ServletException(e1);
        }
        try {
            tid = Integer.parseInt(tids);
        } catch (NumberFormatException e2) {
            error = "Bad topic ID input";
            // throw new ServletException(e2);
        }
        try {
            mDAO.createMessage(t, b, d, tid);
        } catch (SQLException e3) {
            error = "Bad database insertion input";
            // throw new ServletException(e3);
        }
        if (error != null) {
            response.sendError(505, error);
        } else {
            String path = "/TIW-ExampleMessage/GetMessagesOfTopic?topicid=" + tids + "&topicname=" + tname;
            response.sendRedirect(path);
        }
    }
}
