package hh.sof3as3.Reseptikirjasto.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Comment {
	
	// attribuutit: 
	
	// yksilöivä id-arvo
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id;
	
	// viestin sisältö
	@Size(min=2, max=250)
	private String message;
	
	// aikaleima milloin viesti lähetetty
	private Timestamp timestamp;
	
	// viiteavainattribuutti käyttäjälle joka luo kommentin
	@JsonIgnoreProperties({"password", "role", "myRecipes", "myComments"})
	@ManyToOne
	@JoinColumn(name="commenterid")
	private User commenter;
	
	// viiteavainattribuutti reseptille
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="recipeid")
	private Recipe recipe;

	// konstruktorit: 
	
	public Comment() {
		this.message = null;
		this.timestamp = null;
		this.commenter = null;
		this.recipe = null;
	}
	public Comment(String message, Timestamp timestamp, User commenter, Recipe recipe) {
		this.message = message;
		this.timestamp = timestamp;
		this.commenter = commenter;
		this.recipe = recipe;
	}
	
	// getterit ja setterit:
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public String getTimestampStr() { // palauttaa timestampin formatoituna stringinä
		return new SimpleDateFormat("d.M.yyyy H:mm:ss").format(timestamp);
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public User getCommenter() {
		return commenter;
	}
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	// toString: 
	
	@Override
	public String toString() {
		return "id=" + id + ", message=" + message + ", timestamp=" + timestamp + ", commenter=" + commenter.getUsername() + ", recipe=" + recipe.getName();
	}
}
