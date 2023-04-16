package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;

@RestController
public class CategoryRestController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	// hakee ja palauttaa json-muodossa kaikki kategoriat
	@GetMapping("/categories")
	public List<Category> getCategoriesRest() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	// hakee ja palauttaa json-muodossa yhden kategorian
	@GetMapping("/categories/{categoryid}")
	public Optional<Category> getCategoryRest(@PathVariable("categoryid") Long categoryid) {
		return categoryRepository.findById(categoryid);
	}
	
	// hakee ja palauttaa json-muodossa yhden kategorian reseptit
	@GetMapping("/categories/{categoryid}/recipes")
	public List<Recipe> getCategoryRecipesRest(@PathVariable("categoryid") Long categoryid) {
		Category category = categoryRepository.findById(categoryid).get();
		return category.getRecipes();
	}

}
