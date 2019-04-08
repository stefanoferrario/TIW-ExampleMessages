package it.polimi.tiw.messages.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import it.polimi.tiw.messages.beans.Message;
import java.time.*;


public class MessageDAO {
	private Connection con;

	public MessageDAO(Connection connection) {
		this.con = connection;
	}

	public List<Message> findMessagesByTopic(int topicId) throws SQLException {
		List<Message> messages = new ArrayList<Message>();
		String query = "SELECT idmessage, title, body, date FROM message WHERE topicid = ?";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = con.prepareStatement(query);
			pstatement.setInt(1, topicId);
			result = pstatement.executeQuery();
			while (result.next()) {
				Message message = new Message();
				message.setId(result.getInt("idmessage"));
				message.setTitle(result.getString("title"));
				message.setBody(result.getString("body"));
				message.setDate(result.getDate("date"));
				messages.add(message);
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException(e1);
			}
			try {
				pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}

		return messages;
	}

	public void createMessage(String t, String b, Date d, int tid) throws SQLException {
		String query = "INSERT into dbtest.message (title, body, date, topicid)   VALUES(?, ?, ?, ?)";
		//String query = "INSERT into dbtest.message (title, body, topicid)  VALUES(?, ?, ?)";
		int code = 0;
		PreparedStatement pstatement = null;
		try {
			pstatement = con.prepareStatement(query);
			pstatement.setString(1, t);
			pstatement.setString(2, b);
			pstatement.setObject(3, d.toInstant().atZone(ZoneId.of("Europe/Rome")).toLocalDate());
			pstatement.setInt(4, tid);
			code = pstatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			try {
				pstatement.close();
			} catch (Exception e1) {

			}
		}
	}

}
