package forumtest.client;


import java.io.Serializable;

import forumtest.server.User;


public class Topic extends Post implements Serializable {


	
	private String text;
	private boolean is_topic;
	private String user;
	private int id;
	private int topic;

	public Topic(){
		
	}
	
	public Topic(String text, String user, boolean is_topic){
		this.text = text;
		this.user = user;
		this.is_topic = is_topic;
	}
	
	public Topic(int id, String text, String user, boolean is_topic, int topic){
		this.id = id;
		this.text = text;
		this.user = user;
		this.is_topic = is_topic;
		this.topic = topic;
		
	}

	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	public String getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	public int getTopic() {
		// TODO Auto-generated method stub
		return topic;
	}
	
	public boolean isTopic() {
		// TODO Auto-generated method stub
		return is_topic;
	}

	public int getId() {
		return id;
	}

	public void setId(int idNr) {
		id = idNr;
	}

	

}
