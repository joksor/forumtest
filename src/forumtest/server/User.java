package forumtest.server;

import java.io.Serializable;

public class User implements Serializable {

	private String name;
	private String password;
	private boolean is_admin;
	
	public User(){
		
	}

	public User(String name, String password, boolean is_admin){
		this.name = name;
		this.setPassword(password);
		this.setIs_admin(is_admin); 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}
}
