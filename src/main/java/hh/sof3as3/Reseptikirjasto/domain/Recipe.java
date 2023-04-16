package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Recipe {
	
	// attribuutit:
	
	// yksilöivä id-arvo
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id;
	
	// reseptin nimi
	@Size(min=3, max=40)
	private String name;
	
	// annosmäärä
	@Range(min=1, max=99)
	private int numberOfServings;
	
	// valmistusaika
	@NotNull // vaikka validointi voisi olla spesifimpi kuin notnull, nyt jos recipeformilla syötetään aika väärässä muodossa, bindingresult vie kuitenkin halutusti takaisin lomakkeelle
	private LocalTime time;
	
	// valmistukseen tarvittavat ainesosat
	@Size(min=5, max=800)
	private String listOfIngredients;
	
	// valmistusohjeet
	@Size(min=5, max=800) 
	private String instructions;
	
	// viiteavainattribuutti kategorialle
	@JsonIgnoreProperties({"color"}) // jotta rest-endpointit /recipes ja /recipes/id näyttäisivät kategoriasta pelkän id:n ja nimen
	@ManyToOne
	@JoinColumn(name="categoryid")
	private Category category;
	
	// viiteavainattribuutti reseptin tekijälle
	@JsonIgnoreProperties({"role", "myRecipes", "myComments"}) // rooli blokataan jotta /recipes ja /recipes/id näyttäisivät vain id:n ja käyttäjätunnuksen. myRecipes ja myComments blokataan jotta vältetään loputon loop
	@ManyToOne
	@JoinColumn(name="authorid")
	private User author;
	
	// viiteavainattribuutti kommenteille
	@JsonIgnore // blokataan jottei näy endpointeissa /recipes ja /recipes/id
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private List<Comment> comments;
	
	// viiteavainattribuutti tykkäyksille
	// ManyToMany-yhteys: määritellään recipen ja userin välille välitaulu, joka käyttää viiteavaimina molempien id:arvoja
	@JsonIgnore // blokataan jotta vältetään loputon loop
	@ManyToMany
	@JoinTable(
			name = "recipeLike", 
			joinColumns = @JoinColumn(name = "recipeid"), 
			inverseJoinColumns = @JoinColumn(name = "userid")
		   )
	private List<User> likes;
	
	// konstruktorit:
	
	public Recipe() {
		this.name = null;
		this.numberOfServings = 1;
		this.time = null;
		this.listOfIngredients = null;
		this.instructions = null;
		this.category = null;
		this.author = null;
	}
	public Recipe(String name, int numberOfServings, LocalTime time, String listOfIngredients, String instructions, Category category, User author) {
		this.name = name;
		this.numberOfServings = numberOfServings;
		this.time = time;
		this.listOfIngredients = listOfIngredients;
		this.instructions = instructions;
		this.category = category;
		this.author = author;
	}
	
	// getterit ja setterit:
	
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
	public LocalTime getTime() {
		return time;
	}
	public String getTimeStr() { // kustomoitu getteri joka palauttaa valmistusajan luettavammassa muodossa (x h x min)
		String timeStr = "";
		int hrs = time.getHour();
		int mins = time.getMinute();
		if (hrs != 0) {
			timeStr += hrs + " h ";
		}
		if (mins != 0) {
			timeStr += mins + " min";
		}
		return timeStr;
	}
	public void setTime(LocalTime time) {
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
	public List<User> getLikes() {
		return likes;
	}
	public void setLikes(List<User> likes) {
		this.likes = likes;
	}
	
	// toString:
	
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", numberOfServings=" + numberOfServings + ", time=" + time
				+ ", listOfIngredients=" + listOfIngredients + ", instructions=" + instructions 
				+ ", category=" + ((category != null) ? category.getName() : "") // jos tyhjä niin palauttaa ""
				+ ", author=" + ((author != null) ? author.getUsername() : ""); // jos tyhjä niin palauttaa ""
	}
	

}
