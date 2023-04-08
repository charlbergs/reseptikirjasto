package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	// todo: kirjautuminen ja roolit
	
	// attribuutit
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	@JsonIgnore // piilottaa salasanan kaikissa rest-pyynnöissä (myös data rest)
	private String password;
	private String role;
	// viiteavainattribuutti käyttäjän luomille resepteille
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	private List<Recipe> myRecipes;
	// viiteavainattribuutti käyttäjän luomille kommenteille
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commenter")
	private List<Comment> myComments;
	
	// konstruktorit
	public User() {
		this.username = null;
		this.password = null;
		this.role = null;
	}
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Recipe> getMyRecipes() {
		return myRecipes;
	}
	public void setMyRecipes(List<Recipe> myRecipes) {
		this.myRecipes = myRecipes;
	}
	public List<Comment> getMyComments() {
		return myComments;
	}
	public void setMyComments(List<Comment> myComments) {
		this.myComments = myComments;
	}
	
	// toString
	@Override
	public String toString() {
		return "id=" + id + ", username=" + username + ", password=" + password + ", role=" + role;
	}
	
	
}
