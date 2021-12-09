package model_user;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable{
	
	private int id;
	private String username;
	private String password;
	private String email;
	private boolean isAdmin;
	
	private static int idCounter = 1;

	public User(String username) {
		this.id = idCounter++;
		this.username = username;
		this.password = "";
		this.email = "";
		this.isAdmin = false;
	}
	
	public User(String username, String password, String email) {
		this.id = idCounter++;
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = false;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + "]";
	}

	@Override
	public int compareTo(User o) {
		if (password.compareTo(o.password) == 0) {
			return 0;
		} else if (password.compareTo(o.password) > 0) {
			return 1;
		} else {
			return -1;
		}
	}

}
