package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;

@RestController
public class RecipeRestController {
	
	// repositoriot
	@Autowired
	private RecipeRepository recipeRepository;
	
	// hakee ja palauttaa json-muodossa kaikkien reseptien tiedot
	@GetMapping("/recipes")
	public List<Recipe> recipeListRest() {
		return (List<Recipe>) recipeRepository.findAll();
	}
	
	// hakee ja palauttaa json-muodossa yhden reseptin tiedot
	@GetMapping("/recipes/{recipeid}")
	public Optional<Recipe> getRecipeRest(@PathVariable("recipeid") Long recipeid) {
		return recipeRepository.findById(recipeid);
	}
	
	// hakee ja palauttaa json-muodossa yhden reseptin kaikki kommentit
	@GetMapping("/recipes/{recipeid}/comments")
	public List<Comment> commentListRest(@PathVariable("recipeid") Long recipeid) {
		Recipe recipe = recipeRepository.findById(recipeid).get();
		return recipe.getComments();
	}

}
