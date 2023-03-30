package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;

@Controller
public class RecipeController {
	
	// repositoriot
	@Autowired
	private RecipeRepository recipeRepository;
	
	// todo: - reseptien lisäys, muokkaus, poisto
	
	// tulostustesti
	@GetMapping("/recipelist")
	public String getIndex(Model model) {
		// haetaan tietokantaan tallennetut reseptit listalle
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		// välitetään templatelle
		model.addAttribute("recipes", recipes);
		return "recipelist";
	}

}
