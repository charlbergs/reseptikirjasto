package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;
import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.CommentRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;
import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
@EnableMethodSecurity(securedEnabled = true)
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
	
	// apumetodi kirjautuneen käyttäjän hakemiseen
	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authentication.getName());
		return user;
	}
	
	// kaikkien reseptien listausnäkymä, GET
	@GetMapping("/recipelist")
	public String getAllRecipes(Model model) {
		// haetaan tietokantaan tallennetut reseptit listalle
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		// välitetään templatelle
		model.addAttribute("recipes", recipes);
		return "recipelist";
	}
	
	// yksittäisen reseptin katselunäkymä, GET
	@GetMapping("/recipe/{recipeid}")
	public String viewRecipe(@PathVariable("recipeid") Long recipeid, Model model) {
		User user = getCurrentUser();
		// välitetään valittu resepti templatelle
		Recipe recipe = recipeRepository.findById(recipeid).get();
		model.addAttribute("recipe", recipe);
		// välitetään tieto onko käyttäjällä reseptin muokkaus- ja poisto-oikeus
		if (user != null && (recipe.getAuthor() == user || user.getRole() == "ADMIN")) {
			model.addAttribute("recipeOwner", "isRecipeOwner");
		} else {
			model.addAttribute("recipeOwner", "isNotRecipeOwner");
		}
		// välitetään tieto onko kirjautunut käyttäjä tykännyt reseptistä (tykkää / peru tykkäys -painike)
		if (user != null && user.getLikedRecipes().contains(recipe)) {
			model.addAttribute("like", "showUnlike");
		} else {
			model.addAttribute("like", "showLike");
		}
		// välitetään valitun reseptin kommentit
		model.addAttribute("comments", commentRepository.findByRecipe(recipe));
		// välitetään kommenttiolio uuden kommentin luomista varten
		Comment comment = new Comment();
		comment.setCommenter(user); // asetetaan kirjautunut käyttäjä kommentin tekijäksi
		comment.setRecipe(recipe); // asetetaan valittu resepti kommentin resepti-attribuutiksi
		model.addAttribute("newComment", comment);
		return "recipeview";
	}
	
	// käyttäjän omat reseptit, GET
	@GetMapping("/myrecipes")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String getMyRecipes(Model model) {
		// haetaan tietokantaan tallennetut reseptit listalle
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findByAuthor(getCurrentUser());
		// välitetään templatelle
		model.addAttribute("recipes", recipes);
		return "myrecipes";
	}
	
	// reseptin lisäys, GET
	@GetMapping("/addrecipe")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // vain kirjautunut käyttäjä tai admin
	public String addNewRecipe(Model model) {
		// tyhjä reseptiolio tallentamista varten
		Recipe recipe = new Recipe();
		// asetetaan kirjautunut käyttäjä reseptin tekijäksi
		recipe.setAuthor(getCurrentUser());
		// välitetään templatelle
		model.addAttribute("recipe", recipe);
		model.addAttribute("categories", categoryRepository.findAll()); // kategoriat
		model.addAttribute("header", "Uusi resepti"); // oikea otsikko lomakkeelle
		return "recipeform";
	}

	// reseptin muokkaus, GET
	@GetMapping("/editrecipe/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String editRecipe(@PathVariable("id") Long id, Model model) {
		// tarkistetaan että kirjautunut käyttäjä on reseptin tekijä tai admin,
		// muuten muokkauslomakkeen haku epäonnistuu
		User user = getCurrentUser();
		Recipe recipe = recipeRepository.findById(id).get();
		if (recipe.getAuthor() == user || user.getRole() == "ADMIN") {
			model.addAttribute("recipe", recipeRepository.findById(id)); // välitetään templatelle oikea resepti id:n avulla 
			model.addAttribute("categories", categoryRepository.findAll()); // välitetään templatelle kategoriat
			model.addAttribute("header", "Muokkaa reseptiä"); // välitetään oikea otsikko lomakkeelle
			return "recipeform"; // uudelleenohjataan listausnäkymään
		} else {
			return "redirect:/recipelist";
		}
	}
	
	// reseptin lisäys/muokkaus, POST
	@PostMapping("/saverecipe")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // vain kirjautunut käyttäjä tai admin
	public String postRecipeForm(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // validointi: jos lähetetyissä tiedoissa virheitä, palataan formille
			if (bindingResult.hasFieldErrors("time")) { // ajalle oma tarkentava errorviesti (antaa myös pidemmän notnull-errorviestin)
				bindingResult.rejectValue("time", "err.timeValueRejected", "give value as hh.mm / h.mm / hh:mm");
			}
			model.addAttribute("categories", categoryRepository.findAll()); // välitetään kategoriat uudestaan koska muuten tyhjä
			return "recipeform";
		} else { // validointi: jos lähetetyissä tiedoissa ei virheitä, lähetys onnistuu ja resepti tallennetaan repositorioon
			recipeRepository.save(recipe);
			return "redirect:/recipe/" + recipe.getId(); // uudelleenohjataan reseptinäkymään
    	}
	}
	
	// reseptin poistaminen, POST
	@GetMapping("/deleterecipe/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String deleteRecipe(@PathVariable("id") Long id) {
		User user = getCurrentUser();
		Recipe recipe = recipeRepository.findById(id).get();
		// tarkistetaan että kirjautunut käyttäjä on reseptin tekijä tai admin
		if (recipe.getAuthor() == user || user.getRole() == "ADMIN") {
			recipeRepository.deleteById(id);
		}
		return "redirect:/recipelist"; // uudelleenohjataan listausnäkymään
	}

}
