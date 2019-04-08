package it.polimi.tiw.messages.dao;

import it.polimi.tiw.messages.beans.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO {
	private Connection con;

	public TopicDAO(Connection connection) {
		this.con = connection;
	}

	public List<Topic> findAllTopics() throws SQLException {
		List<Topic> topics = new ArrayList<Topic>();
		String query = "SELECT * FROM dbtest.topic";
		ResultSet result = null;
		PreparedStatement pstatement = null;
		try {
			pstatement = con.prepareStatement(query);
			result = pstatement.executeQuery();
			while (result.next()) {
				Topic t = new Topic();
				t.setId(result.getInt("idTopic"));
				t.setName(result.getString("name"));
				topics.add(t);
			}
		} catch (SQLException e) {
			throw new SQLException(e);

		} finally {
			try {
				result.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close result");
			}
			try {
				pstatement.close();
			} catch (Exception e1) {
				throw new SQLException("Cannot close statement");
			}
		}
		return topics;
	}

}
