package hh.sof3as3.Reseptikirjasto.web;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.CommentRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@Controller
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired UserRepository userRepository;
	
	// kommentin lisäys
	@PostMapping("/savecomment")
	public String saveComment(Comment comment) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		comment.setTimestamp(timestamp); // asetetaan timestamp
		commentRepository.save(comment);
		return "redirect:/recipe/" + comment.getRecipe().getId(); // uudelleenohjaus takaisin reseptinäkymään
	}
	
	// kommentin poistaminen
	@GetMapping("/deletecomment/{recipeid}/{commentid}") // reseptin id redirectiä ja kommentin id kommentin poistamista varten
	public String deletecomment(@PathVariable("recipeid") Long recipeid, @PathVariable("commentid") Long commentid) {
		commentRepository.deleteById(commentid);
		return "redirect:/recipe/" + recipeid; // uudelleenohjaus takaisin reseptinäkymään
	}

}
