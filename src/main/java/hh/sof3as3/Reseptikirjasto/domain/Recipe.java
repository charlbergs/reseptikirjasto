package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe {
	
	// attribuutit
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id; // yksilöivä id-arvo
	private String name; // reseptin nimi
	private int numberOfServings; // annosmäärä
	private int time; // valmistusaika minuutteina
	@Column(length=1000) private String listOfIngredients; // tarvittavat ainesosat (column lengthillä maksimimerkkimäärä, koska muuten sql ei huoli pitkiä tekstejä)
	@Column(length=1000) private String instructions; // valmistusohjeet
	// viiteavainattribuutti kategorialle
	@JsonIgnoreProperties({"color"})
	@ManyToOne
	@JoinColumn(name="categoryid")
	private Category category;
	// viiteavainattribuutti reseptin tekijälle
	@JsonIgnoreProperties({"role", "myRecipes", "myComments"}) // blokataan tekijän myRecipes ja myComments jotta vältetään loputon loop
	@ManyToOne
	@JoinColumn(name="authorid")
	private User author;
	// viiteavainattribuutti kommenteille
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Comment> comments;
	
	// konstruktorit
	public Recipe() {
		this.name = null;
		this.numberOfServings = 0;
		this.time = 0;
		this.listOfIngredients = null;
		this.instructions = null;
		this.category = null;
		this.author = null;
	}
	public Recipe(String name, int numberOfServings, int time, String listOfIngredients, String instructions, Category category, User author) {
		this.name = name;
		this.numberOfServings = numberOfServings;
		this.time = time;
		this.listOfIngredients = listOfIngredients;
		this.instructions = instructions;
		this.category = category;
		this.author = author;
	}
	
	// getterit ja setterit
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfServings() {
		return numberOfServings;
	}
	public void setNumberOfServings(int numberOfServings) {
		this.numberOfServings = numberOfServings;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getListOfIngredients() {
		return listOfIngredients;
	}
	public void setListOfIngredients(String listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	// toString
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", numberOfServings=" + numberOfServings + ", time=" + time
				+ ", listOfIngredients=" + listOfIngredients + ", instructions=" + instructions 
				+ ", category=" + category.getName() + ", user=" + author.getUsername();
	}
	

}
