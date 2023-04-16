package hh.sof3as3.Reseptikirjasto.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;
import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@Controller
@EnableMethodSecurity(securedEnabled = true)
public class LikeController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RecipeRepository recipeRepository;
	
	// apumetodi kirjautuneen käyttäjän hakemiseen
	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authentication.getName());
		return user;
	}
	
	// näyttää reseptin tykkäykset, GET
	@GetMapping("/recipe/{recipeid}/likes")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String showLikes(@PathVariable("recipeid") Long recipeid, Model model) {
		Recipe recipe = recipeRepository.findById(recipeid).get();
		List<User> users = recipe.getLikes();
		model.addAttribute("users", users);
		return "recipelikes";
	}
	
	// näyttää kirjautuneen käyttäjän tykätyt reseptit, GET
	@GetMapping("/mylikes")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String showMylikes(Model model) {
		User user = getCurrentUser();
		List<Recipe> recipes = user.getLikedRecipes();
		model.addAttribute("recipes", recipes);
		return "mylikes";
	}
	
	// tykkäyksen lisäys, POST
	// varmuudeksi lisätään molempiin manytomany-yhteyden päihin, 
	// eli user lisätään recipen likes-attribuuttiin, ja recipe userin likedRecipes-attribuuttiin.
	// (periaatteessa toimii myös ilman jälkimmäistä)
	@PostMapping("/addlike/{recipeid}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String addLike(@PathVariable("recipeid") Long recipeid) {
		User user = getCurrentUser(); // haetaan kirjautunut käyttäjä
		Recipe recipe = recipeRepository.findById(recipeid).get(); // haetaan resepti
		
		// lisätään käyttäjä reseptin tykkääjiin
		List<User> likes = new ArrayList<User>(); // luodaan lista käyttäjille
		if (recipe.getLikes() != null) {
			likes = recipe.getLikes(); // jos repositoryn lista ei tyhjä niin haetaan tykkääjät listalle
		}
		if (!likes.contains(user)) {
			likes.add(user); // lisätään käyttäjä tykkääjiin jos ei vielä listalla
			recipe.setLikes(likes); // asetetaan lista reseptille
			recipeRepository.save(recipe); // viedään tietokantaan
		}
		// lisätään resepti käyttäjän tykättyihin resepteihin
		List<Recipe> likedRecipes = new ArrayList<Recipe>(); // luodaan lista tykkäyksille
		if (user.getLikedRecipes() != null) {
			likedRecipes = user.getLikedRecipes(); // jos repositoryn lista ei tyhjä niin haetaan käyttäjän tykkäykset listalle
		}
		if (!likedRecipes.contains(recipe)) {
			likedRecipes.add(recipe); // lisätään resepti tykkääjiin jos ei vielä listalla
			user.setLikedRecipes(likedRecipes); // asetetaan lista käyttäjälle
			userRepository.save(user); // viedään tietokantaan
		}
		
		return "redirect:/recipe/" + recipe.getId(); // palataan reseptinäkymään
	}
	
	// tykkäyksen poisto, POST
	// myös poisto tehdään molemmissa päissä, eli
	// user poistetaan recipen likes-attribuutista, ja recipe userin likedRecipes-attribuutista
	@PostMapping("/deletelike/{recipeid}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String deleteLike(@PathVariable("recipeid") Long recipeid) {
		User user = getCurrentUser(); // haetaan kirjautunut käyttäjä
		Recipe recipe = recipeRepository.findById(recipeid).get(); // haetaan resepti
		
		// poistetaan käyttäjä reseptin tykkääjistä
		List<User> likes = recipe.getLikes();
		likes.remove(user);
		recipe.setLikes(likes);
		recipeRepository.save(recipe);
		// poistetaan resepti käyttäjän tykkäyksistä
		List<Recipe> likedRecipes = user.getLikedRecipes();
		likedRecipes.remove(recipe);
		user.setLikedRecipes(likedRecipes);
		userRepository.save(user);
		return "redirect:/recipe/" + recipe.getId();
	}
	

}
