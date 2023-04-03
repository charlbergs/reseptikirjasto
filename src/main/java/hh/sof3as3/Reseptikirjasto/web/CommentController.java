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

@Controller
public class CommentController {
	
	@Autowired
	private CommentRepository commentRepository;
	
	// kommentin lisäys
	@PostMapping("/savecomment")
	public String saveComment(Comment comment, Recipe recipe) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		comment.setTimestamp(timestamp); // timestamp
		comment.setRecipe(recipe);
		commentRepository.save(comment);
		return "redirect:/recipe/1"; // todo: vaihda
	}
	
	// kommentin poistaminen
	@GetMapping("/deletecomment/{id}")
	public String deletecomment(@PathVariable("id") Long id) {
		commentRepository.deleteById(id);
		return "redirect:/recipe/1"; // todo: vaihda
	}

}
