package hh.sof3as3.Reseptikirjasto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hh.sof3as3.Reseptikirjasto.web.CategoryController;
import hh.sof3as3.Reseptikirjasto.web.CommentController;
import hh.sof3as3.Reseptikirjasto.web.LikeController;
import hh.sof3as3.Reseptikirjasto.web.RecipeController;
import hh.sof3as3.Reseptikirjasto.web.UserController;

@SpringBootTest
class ReseptikirjastoApplicationTests {
	
	// controllerien smoketestit: testataan että kaikki controllerit pystytään lataamaan
	
	// testattavat controllerit
	@Autowired
	private RecipeController recipeController;
	@Autowired
	private CategoryController categoryController;
	@Autowired
	private UserController userController;
	@Autowired
	private CommentController commentController;
	@Autowired
	private LikeController likeController;

	@Test
	public void recipeControllerLoads() throws Exception {
		assertThat(recipeController).isNotNull();
	}
	
	@Test
	public void categoryControllerLoads() throws Exception {
		assertThat(categoryController).isNotNull();
	}
	
	@Test
	public void userControllerLoads() throws Exception {
		assertThat(userController).isNotNull();
	}
	
	@Test
	public void commentControllerLoads() throws Exception {
		assertThat(commentController).isNotNull();
	}
	
	@Test
	public void likeControllerLoads() throws Exception {
		assertThat(likeController).isNotNull();
	}

}
