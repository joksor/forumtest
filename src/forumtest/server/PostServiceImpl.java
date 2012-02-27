package forumtest.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import forumtest.client.Post;
import forumtest.client.PostService;
import forumtest.client.Topic;

public class PostServiceImpl extends RemoteServiceServlet implements PostService {

	public PostServiceImpl() throws ClassNotFoundException{

		try{
			System.out.println("herro?");
			Class.forName("org.sqlite.JDBC");

			Connection connection = null;

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//statement.executeUpdate("drop table if exists users");
			//statement.executeUpdate("create table users (id integer primary key, username string, password string, is_admin integer)");
			//statement.executeUpdate("insert into users values(null,'test_user','asdf','0')");
			//statement.executeUpdate("insert into users values(null,'admin','admin','1')");
			/*ResultSet rs = statement.executeQuery("select * from team");
	      while(rs.next())
	      {
	        // read the result set
	        System.out.println("name = " + rs.getString("name"));
	        System.out.println("score = " + rs.getInt("score"));
	        System.out.println("diff = " + rs.getInt("diff"));
	      }*/
			connection.close();
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}

	}

	@Override
	public Post[] getPosts(int topic_id) throws Exception{
		System.out.println("getting posts");
		ArrayList<Post> postList = new ArrayList<Post>();
		ResultSet rs = null;
		
		try{
			System.out.println("Getting posts from topic "+topic_id);
			Class.forName("org.sqlite.JDBC");

			Connection connection = null;

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");
			
			System.out.println("getPosts connection established");
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			rs = statement.executeQuery("select * from posts where topic="+topic_id+" and is_topic=0");
			System.out.println("getPost selecting");
	      while(rs.next())
	      {
	        System.out.println(rs.getString("text"));
	    	postList.add(new Post(rs.getString("text"),rs.getString("user"), rs.getInt("topic"), false));
	      }
	      //rs.close();
	      //connection.close();
	      System.out.println("här?");
	      Post[] posts = new Post[postList.size()];
	      for(int i=0;i<posts.length;i++){
	    	  System.out.println(postList.get(i).getTopic());
	    	  posts[i] = postList.get(i);
	    	  System.out.println("tototot??");
	      }
	      System.out.println("tototot");
	      return posts;
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Post addPost(Post post) throws Exception {
			int id = 0;
		
			int isTopic = 0;
			if(post.isTopic())
				isTopic=1;

		
			try{
				Class.forName("org.sqlite.JDBC");
				// create a database connection
				Connection connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");
				System.out.println("addPost connection established");
				System.out.println(post.getText());
				Statement statement = connection.createStatement();

				PreparedStatement prep2 = connection.prepareStatement("insert into posts values(null,'"+post.getText()+"','"+post.getUser()+"','"+post.getTopic()+"','"+isTopic+"')");
				prep2.execute();
			
			//statement.executeUpdate("insert into posts values(null,'hejhej','jocke',1,0)");
			//statement.executeUpdate("insert into team values('MANCITY',85,44)");
			System.out.println("insert done.");
			ResultSet rs2 = statement.executeQuery("select * from posts");
			System.out.println("addPost selecting");

			connection.close();
			rs2.close();
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		return post;
	}

	public void createTopic(Topic topic){
		//Adds the topic to the database
		
		
	}

	@Override
	public Topic[] getTopics() throws Exception {
		System.out.println("getting topics");
		ArrayList<Topic> ar = new ArrayList<Topic>();
		ResultSet rs = null;
		int i = 0;
		
		try{

			Class.forName("org.sqlite.JDBC");

			Connection connection = null;

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			rs = statement.executeQuery("select * from posts where is_topic=1");
			
			while(rs.next())
			{
				ar.add(new Topic(rs.getInt("id"),rs.getString("text"),rs.getString("user"), true, rs.getInt("topic")));
				// read the result set
				System.out.println("dhf33hfg");
				System.out.println("id = " + rs.getInt("id"));
				System.out.println("text = " + rs.getString("text"));
				System.out.println("user = " + rs.getString("user"));
				System.out.println("topic = " + rs.getInt("topic"));
				System.out.println("is_topic = " + rs.getInt("is_topic"));
			}
			//rs.close();
			//connection.close();
			Topic[] topics = ar.toArray(new Topic[ar.size()]);
			System.out.println("asf");
			//System.out.println(topics[0].getText());
			return topics;
			
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
			return null;
		}
		
	}

	@Override
	public Post[] addTopic(Post[] newTopic) throws Exception {
		//Post[] newEntry = new Post[2];
		int i=0;
		int id=0;
		try{

			Class.forName("org.sqlite.JDBC");

			Connection connection = null;

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet rs1 = statement.executeQuery("select count(*) from posts");
			while(rs1.next()){
				id = rs1.getInt(1);
				System.out.println(id);
			}
			rs1.close();
			
			
			PreparedStatement prep= connection.prepareStatement("insert into posts values(null,'"+newTopic[0].getText()+"','"+newTopic[0].getUser()+"','"+(id+1)+"','"+1+"')");
			newTopic[0].setId(id+1);
			System.out.println(id+1);
			System.out.println(newTopic[0].getId());
			prep.execute();
			
			
			
			PreparedStatement prep2 = connection.prepareStatement("insert into posts values(null,'"+newTopic[1].getText()+"','"+newTopic[1].getUser()+"','"+(id+1)+"','"+0+"')");
			prep2.execute();
			newTopic[1].setTopic(id+1);

			return newTopic;
			
		}
		catch(SQLException e)
		{
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
			return null;
		}
		
	}


}
