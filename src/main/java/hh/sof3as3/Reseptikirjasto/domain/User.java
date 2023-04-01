package hh.sof3as3.Reseptikirjasto.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	// todo: kirjautuminen - lisää password ja role
	
	// attribuutit
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	
	// konstruktorit
	public User() {
		this.username = null;
	}
	public User(String username) {
		this.username = username;
	}
	
	// getterit ja setterit
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	// toString
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	
	
}
