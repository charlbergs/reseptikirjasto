package hh.sof3as3.Reseptikirjasto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;

@DataJpaTest
public class CategoryRepositoryTests {
	
	// testataan categoryrepositoryn metodeita: findAll(), findById(), save() (add ja edit), deleteById()
	// testeissä käytetään commandlinerunnerin testidataa
	
	// repositoriot
	@Autowired 
	private CategoryRepository categoryRepository;
	
	// kaikkien kategorioiden hakeminen: tarkistaa että löytyy 3 kpl
	@Test
	public void findAllCategoriesTest() {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		assertThat(categories).hasSize(3);
	}
	
	// yhden kategorian haku: tarkistaa että löytää oikean kategorian nimellä
	@Test
	public void findCategoryTest() {
		Category category = categoryRepository.findById((long) 1).orElse(null);
		assertThat(category.getName()).contains("Jälkiruoat");
	}
	
	// kategorian lisääminen: tarkistaa että uudelle kategorialle generoituu id (kertoo että tallennus repositorioon onnistui)
	@Test
	public void addCategoryTest() {
		Category category = new Category("Testikategoria", "#0000ff");
		categoryRepository.save(category);
		assertThat(category.getId()).isNotNull();
	}
	
	// kategorian muokkaaminen: haetaan yksi kategoria ja muutetaan tietoja, tallennetaan repositorioon,
	// ja haetaan uudelleen uuteen muuttujaan ja tarkistetaan että muutos löytyy tiedoista
	@Test
	public void editCategoryTest() {
		Category category = categoryRepository.findById((long) 2).orElse(null);
		category.setColor("#ffffff");
		categoryRepository.save(category);
		Category category2 = categoryRepository.findById((long) 2).orElse(null);
		assertThat(category2.getColor()).contains("#ffffff");
	}
	
	// kategorian poistaminen: tarkistetaan että poiston jälkeen kategorioita on 2 kpl
	@Test
	public void deleteCategoryTest() {
		categoryRepository.deleteById((long) 3);
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		assertThat(categories).hasSize(2);
	}
	
}
