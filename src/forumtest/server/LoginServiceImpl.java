package forumtest.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import forumtest.client.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	public LoginServiceImpl(){
		
	}

	@Override
	public int[] logIn(String user, String password) throws Exception {
		int[] loginInfo = new int[2];
		int sessionID = 0;
		int is_admin = 0;
		try{
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			Connection connection = DriverManager.getConnection("jdbc:sqlite:forumdb.db");
			System.out.println("login connection established");
			System.out.println(user+" login with pw: "+password);
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select id, is_admin from users where username='"+user+"' and password='"+password+"'");
			if(rs.next()){
				sessionID = rs.getInt(1);
				is_admin = rs.getInt(2);
			}
			loginInfo[0] = sessionID;
			loginInfo[1] = is_admin;
			System.out.println(sessionID);
			System.out.println("login?");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return loginInfo;
	}

}
