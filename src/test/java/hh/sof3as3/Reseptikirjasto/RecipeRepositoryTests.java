package hh.sof3as3.Reseptikirjasto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;
import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@DataJpaTest
public class RecipeRepositoryTests {
	
	// testataan reciperepositoryn metodeita: findAll(), findById(), save() (add ja edit), deleteById(),
	// sekä käyttäjän kaikki omat reseptit palauttava findByAuthor()
	// testeissä käytetään commandlinerunnerin testidataa
	
	// repositoriot
	@Autowired 
	private RecipeRepository recipeRepository;
	@Autowired
	private UserRepository userRepository;
	
	// kaikkien reseptien hakeminen: tarkistaa että löytyy 7 kpl
	@Test
	public void findAllRecipesTest() {
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		assertThat(recipes).hasSize(7);
	}
	
	// yhden reseptin haku: tarkistaa että löytää oikean reseptin nimellä
	@Test
	public void findRecipeTest() {
		Recipe recipe = recipeRepository.findById((long) 1).orElse(null);
		assertThat(recipe.getName()).contains("Munakas");
	}
	
	// reseptin lisääminen: tarkistaa että uudelle reseptille generoituu id (kertoo että tallennus repositorioon onnistui)
	@Test
	public void addRecipeTest() {
		Recipe recipe = new Recipe("Testi", 1, LocalTime.of(0, 30), "Testi", "Testi", null, null);
		recipeRepository.save(recipe);
		assertThat(recipe.getId()).isNotNull();
	}
	
	// reseptin muokkaaminen: haetaan yksi resepti ja muutetaan tietoja, tallennetaan repositorioon,
	// ja haetaan uudelleen uuteen muuttujaan ja tarkistetaan että muutos löytyy tiedoista
	@Test
	public void editRecipeTest() {
		Recipe recipe = recipeRepository.findById((long) 3).orElse(null);
		recipe.setNumberOfServings(15);
		recipeRepository.save(recipe);
		Recipe recipe2 = recipeRepository.findById((long) 3).orElse(null);
		assertThat(recipe2.getNumberOfServings()).isEqualTo(15);
	}
	
	// reseptin poistaminen: tarkistetaan että poiston jälkeen reseptejä on 6 kpl
	@Test
	public void deleteRecipeTest() {
		recipeRepository.deleteById((long) 1);
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		assertThat(recipes).hasSize(6);
	}
	
	// reseptien haku reseptin tekijällä: tarkistetaan että palauttaa oikean määrän reseptejä
	@Test
	public void findByAuthorTest() {
		User user = userRepository.findById((long) 2).get(); // user Superkokki
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findByAuthor(user);
		assertThat(recipes).hasSize(4);
	}
}
