package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	// attribuutit:
	
	// yksilöivä id-arvo
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	private Long id; 
	
	// käyttäjätunnus
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	// salasana encryptattuna
	@Column(name="password", nullable=false)
	@JsonIgnore // piilottaa salasanan kaikissa rest-pyynnöissä (myös autogeneroidut data rest -pyynnöt)
	private String passwordHash;
	
	// käyttäjärooli
	@Column(name="role", nullable=false)
	private String role;
	
	// viiteavainattribuutti käyttäjän luomille resepteille
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	private List<Recipe> myRecipes;
	
	// viiteavainattribuutti käyttäjän luomille kommenteille
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "commenter")
	private List<Comment> myComments;
	
	// viiteavainattribuutti tykätyille resepteille
	@JsonIgnore // blokataan jotta vältetään loputon loop
	@ManyToMany(mappedBy = "likes")
	private List<Recipe> likedRecipes;
	
	
	// konstruktorit
	public User() {
		this.username = null;
		this.passwordHash = null;
		this.role = null;
	}
	public User(String username, String passwordHash, String role) {
		this.username = username;
		this.passwordHash = passwordHash;
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
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
	public List<Recipe> getLikedRecipes() {
		return likedRecipes;
	}
	public void setLikedRecipes(List<Recipe> likedRecipes) {
		this.likedRecipes = likedRecipes;
	}
	
	// toString
	@Override
	public String toString() {
		return "id=" + id + ", username=" + username + ", role=" + role;
	}
	
	
}
