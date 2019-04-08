package it.polimi.tiw.messages.beans;
import it.polimi.tiw.messages.beans.Topic;
import java.util.Date;

public class Message {
	private int id;
	private String title;
	private String body;
	private Date date;
	private Topic topic;

	public int getId() {
		return id;
	}

	public void setId(int iden) {
		this.id = iden;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String b) {
		this.body= b;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic t) {
		this.topic = t;
	}
}

