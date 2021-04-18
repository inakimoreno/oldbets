package domain;

import java.io.*;



import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;



@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 
	//@XmlJavaTypeAdapter(IntegerAdapter.class)

	private String username;
	private String password;
	private ArrayList<Bet> bets;
	private boolean isAdmin;
	
	public User() {
		super();
	}

	
	public User(String username, String password, boolean isAdmin) {
		super();
		this.username = username;
		this.password = password;
		bets = new ArrayList<Bet>();
		this.isAdmin = isAdmin;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
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

	public ArrayList<Bet> getBets() {
		return bets;
	}

	public void addBet(Bet bet) {
		this.bets.add(bet);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null) {
			return false;
		}
		User usr = (User)o;
		return this.username.equals(usr.username)&&this.password.equals(usr.password);
	}
	
	public String toString() {
		return this.username+" "+this.password;
	}
	
}
