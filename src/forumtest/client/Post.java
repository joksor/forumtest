package forumtest.client;

import forumtest.server.User;

import java.io.Serializable;

public class Post implements Serializable{
	
	
	private String text;
	private String user;
	private int topic;
	private boolean is_topic;
	private int id;

	public Post(){
		
		//TODO: Add post to the database.
	}

	public Post(String text, String user, int topic, boolean is_topic){
	
		this.text = text;
		this.user = user;
		this.topic = topic;
		this.is_topic = is_topic;
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
	
	public void setTopic(int topic_id) {
		// TODO Auto-generated method stub
		topic = topic_id;
	}
	
	public boolean isTopic() {
		// TODO Auto-generated method stub
		return is_topic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
