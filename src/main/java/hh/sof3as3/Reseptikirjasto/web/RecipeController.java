package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;
import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.CommentRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@Controller
public class RecipeController {
	
	// repositoriot
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	
	// listausnäkymä
	@GetMapping("/recipelist")
	public String getIndex(Model model) {
		// haetaan tietokantaan tallennetut reseptit listalle
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		// välitetään templatelle
		model.addAttribute("recipes", recipes);
		return "recipelist";
	}
	
	// yksittäisen reseptin katselunäkymä
	@GetMapping("/recipe/{id}")
	public String viewRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(id).get()); // välitetään valittu resepti templatelle
		model.addAttribute("comments", commentRepository.findByRecipe(recipeRepository.findById(id).get())); // välitetään valitun reseptin kommentit templatelle
		// välitetään tyhjä kommenttiolio uuden kommentin luomista varten
		Comment comment = new Comment();
		comment.setRecipe(recipeRepository.findById(id).get()); // asettaa valmiiksi kommentin resepti-attribuutiksi reseptinäkymän reseptin
		comment.setUser(userRepository.findByUsername("Admin")); // tilapäisesti kaikki uudet kommentit adminin, todo: muuta niin että välitetään kirjautunut käyttäjä
		model.addAttribute("comment", comment);
		return "recipeview";
	}
	
	// reseptin lisäys: get
	@GetMapping("/addrecipe")
	public String addNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe()); // välitetään templatelle tyhjä reseptiolio tallentamista varten
		model.addAttribute("categories", categoryRepository.findAll()); // välitetään templatelle kategoriat
		model.addAttribute("header", "Uusi resepti"); // välitetään välitetään oikea otsikko lomakkeelle
		return "recipeform";
	}
	
	// reseptin muokkaus: get
	@GetMapping("/edit/{id}")
	public String editRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(id)); // välitetään templatelle oikea resepti id:n avulla 
		model.addAttribute("categories", categoryRepository.findAll()); // välitetään templatelle kategoriat
		model.addAttribute("header", "Muokkaa reseptiä"); // välitetään oikea otsikko lomakkeelle
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
