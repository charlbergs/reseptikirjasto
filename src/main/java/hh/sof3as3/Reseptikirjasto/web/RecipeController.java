package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;

@Controller
public class RecipeController {
	
	// repositoriot
	@Autowired
	private RecipeRepository recipeRepository;
	
	// listausnäkymä
	@GetMapping("/recipelist")
	public String getIndex(Model model) {
		// haetaan tietokantaan tallennetut reseptit listalle
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		// välitetään templatelle
		model.addAttribute("recipes", recipes);
		return "recipelist";
	}
	
	// reseptin lisäys: get
	@GetMapping("/addrecipe")
	public String addNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe()); // välitetään templatelle tyhjä reseptiolio tallentamista varten
		return "recipeform";
	}
	
	// reseptin muokkaus: get
	@GetMapping("/edit/{id}")
	public String editRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(id).get());
		return "recipeform"; // uudelleenohjataan listausnäkymään
	}
	
	// reseptin lisäys/muokkaus: post
	@PostMapping("/saverecipe")
	public String postRecipeForm(Recipe recipe) {
		recipeRepository.save(recipe);
		return "redirect:/recipelist"; // uudelleenohjataan listausnäkymään
	}
	
	// reseptin poistaminen
	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable("id") Long id) {
		recipeRepository.deleteById(id);
		return "redirect:/recipelist";
	}

}
