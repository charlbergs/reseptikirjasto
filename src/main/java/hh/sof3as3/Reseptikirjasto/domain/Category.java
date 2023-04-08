package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
	
	// attribuutit
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id; // yksilöivä id-arvo
	private String name; // kategorian nimi
	private String color; // korostusväri, jolla kategoria näkyy reseptien listausnäkymässä ym (esim. "#ffffff")
	// viiteavainattribuutti resepteille
	@JsonIgnore // blokataan kategorian reseptilista jotta vältetään loputon loop
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category") // todo: mieti cascadetype
	private List<Recipe> recipes;
	
	// konstruktorit
	public Category() {
		this.name = null;
		this.color = null;
	}
	public Category(String name, String color) {
		this.name = name;
		this.color = color;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	// toString
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", color=" + color;
	}

}
