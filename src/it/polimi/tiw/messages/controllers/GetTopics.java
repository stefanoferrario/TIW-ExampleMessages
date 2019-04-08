package it.polimi.tiw.messages.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import it.polimi.tiw.messages.beans.*;
import it.polimi.tiw.messages.dao.TopicDAO;

@WebServlet(urlPatterns = {"/GetTopics"})
public class GetTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetTopics() {
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

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		TopicDAO tDAO = new TopicDAO(connection);
		List<Topic> topics;
		try {
			topics = tDAO.findAllTopics();
			String path = "/WEB-INF/Home.jsp";
			req.setAttribute("topics", topics);
			RequestDispatcher dispatcher = req.getRequestDispatcher(path);
			dispatcher.forward(req, res);
		} catch (

		SQLException e) {
			res.sendError(500, "Database access failed");
		}
	}

	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {
		}
	}
}
