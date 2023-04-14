package hh.sof3as3.Reseptikirjasto.web;

import java.sql.Timestamp;

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

import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.CommentRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;
import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
@EnableMethodSecurity(securedEnabled = true)
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepository;
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
	
	// kommentin lisäys
	@PostMapping("/savecomment")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // vain admin ja kirjautunut käyttäjä
	public String saveComment(@Valid @ModelAttribute("newComment") Comment comment, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("recipe", recipeRepository.findById(comment.getRecipe().getId()).get()); // välitetään resepti uudestaan templatelle
			return "recipeview"; // palataan
		} else {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			comment.setTimestamp(timestamp); // asetetaan timestamp
			commentRepository.save(comment);
			return "redirect:/recipe/" + comment.getRecipe().getId(); // uudelleenohjaus reseptinäkymään
		}
	}
	
	// kommentin poistaminen
	@GetMapping("/deletecomment/{commentid}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public String deletecomment(@PathVariable("commentid") Long commentid) {
		User user = getCurrentUser();
		// haetaan resepti commentid:n avulla
		Recipe recipe = commentRepository.findById(commentid).get().getRecipe();
		// haetaan reseptin id redirectiä varten
		Long recipeid = recipe.getId();
		// poisto onnistuu jos kirjautunut käyttäjä on admin
		if (recipe.getAuthor() == user || user.getRole() == "ADMIN") {
			commentRepository.deleteById(commentid);
		}
		return "redirect:/recipe/" + recipeid; // uudelleenohjaus takaisin reseptinäkymään
	}

}
