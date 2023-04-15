package hh.sof3as3.Reseptikirjasto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof3as3.Reseptikirjasto.domain.Comment;
import hh.sof3as3.Reseptikirjasto.domain.CommentRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;

@DataJpaTest
public class CommentRepositoryTests {
	
	// testataan reciperepositoryn metodeita: findAll(), findById(), save() (add), delete()
	// sekä reseptillä etsivä findByRecipe()
	// käyttää testidataa commandlinerunnerista
	
	// repositoriot
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private RecipeRepository recipeRepository;
	
	// kaikkien kommenttien hakeminen: tarkistaa että palauttaa 5 kpl
	@Test
	public void findAllCommentsTest() {
		List<Comment> comments = (List<Comment>) commentRepository.findAll();
		assertThat(comments).hasSize(5);
	}
	
	// yhden kommentin hakeminen: tarkistaa että löytää saman viestin
	@Test
	public void findCommentTest() {
		Comment comment = commentRepository.findById((long) 3).orElse(null);
		assertThat(comment.getMessage()).contains("<3");
	}
	
	// kommentin lisääminen: tarkistaa että uudelle kommentille generoituu id
	@Test
	public void addCommentTest() {
		Comment comment = new Comment("Testikommentti", null, null, null);
		commentRepository.save(comment);
		assertThat(comment.getId()).isNotNull();
	}
	
	// kommentin poisto: tarkistaa että poistamisen jälkeen kommentteja on 4 kpl
	@Test
	public void deleteCommentTest() {
		commentRepository.deleteById((long) 1);
		List<Comment> comments = (List<Comment>) commentRepository.findAll();
		assertThat(comments).hasSize(4);
	}
	
	// reseptillä haku: tarkistaa että löytää viestejä 2 kpl
	@Test
	public void findByRecipeTest() {
		Recipe recipe = recipeRepository.findById((long) 1).orElse(null);
		List<Comment> comments = (List<Comment>) commentRepository.findByRecipe(recipe);
		assertThat(comments).hasSize(2);
	}
}
