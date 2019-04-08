package it.polimi.tiw.messages.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.messages.beans.Message;
import it.polimi.tiw.messages.dao.MessageDAO;

@WebServlet(urlPatterns = {"/GetMessagesOfTopic"})
public class GetMessagesOfTopic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetMessagesOfTopic() {
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
		String id = req.getParameter("topicid");
		if (id != null) {
			int topicId = 0;
			try {
				topicId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				res.sendError(505, "Bad number format");
			}
			MessageDAO mDao = new MessageDAO(connection);
			List<Message> messages;
			try {
				messages = mDao.findMessagesByTopic(topicId);
				String path = "/WEB-INF/ShowMessages.jsp";
				req.setAttribute("messages", messages);
				RequestDispatcher dispatcher = req.getRequestDispatcher(path);
				dispatcher.forward(req, res);
			} catch (

			SQLException e) {
				res.sendError(500, "Database access failed");
			}
		} else {
			res.sendError(505, "Bad topic ID");
		}
	}
}
