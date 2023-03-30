package hh.sof3as3.Reseptikirjasto.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
	
	// attribuutit
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // autogeneroidaan id
	private Long id; // yksilöivä id-arvo
	private String name; // kategorian nimi
	private String color; // tunnusvärisävy, jolla kategoria näkyy reseptien listausnäkymässä ym (esim. "#ffffff")
	
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
	
	// toString
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", color=" + color;
	}

}
