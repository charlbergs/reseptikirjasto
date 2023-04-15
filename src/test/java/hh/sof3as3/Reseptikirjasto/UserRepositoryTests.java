package hh.sof3as3.Reseptikirjasto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;

@DataJpaTest
public class UserRepositoryTests {

	// testataan reciperepositoryn metodeita: findAll(), findById(), save() (add),
	// sekä käyttäjätunnuksella etsivä findByUsername()
	// (testataan myös deleteä vaikka sitä ei sovelluksessa tällä hetkellä käytetä)
	// testeissä käytetään commandlinerunnerin testidataa
	
	// repositorio
	@Autowired 
	private UserRepository userRepository;
	
	// kaikkien käyttäjien hakeminen: tarkistaa että löytyy 3 kpl
	@Test
	public void findAllUsersTest() {
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users).hasSize(3);
	}
	
	// yhden käyttäjän haku: tarkistaa että löytää saman käyttäjätunnuksen
	@Test
	public void findUserByIdTest() {
		User user = userRepository.findById((long) 2).orElse(null);
		assertThat(user.getUsername()).contains("Superkokki");
	}
	
	// userin lisääminen: tarkistaa että uudelle käyttäjälle generoituu id
	@Test
	public void addUserTest() {
		User user = new User("Testikäyttäjä", "Testisalasana", "USER");
		userRepository.save(user);
		assertThat(user.getId()).isNotNull();
	}
	
	// käyttäjätunnuksella etsiminen: tarkistaa että löytää saman id:n
	@Test
	public void findByUsernameTest() {
		User user = userRepository.findByUsername("Superkokki");
		assertThat(user.getId()).isEqualTo(2);
	}
	
	// extra: käyttäjän poistamisen testaus (sovelluksessa ei tällä hetkellä käyttäjän poisto- tai muokkaustoiminnallisuuksia)
	// EPÄONNISTUU manytomany-yhteyden takia: käyttäjiä joilla on tykkäyksiä ei nykyisellään pysty poistamaan 
	// toimisi jos manytomany-välitaulun konfiguroisi user-päässä, mutta tällöin puolestaan ongelma siirtyy reseptin poistamiseen
	// eli käyttäjän poistotoiminnallisuus vaatisi käyttäjän tykkäysten poistamista ensin
	@Test
	public void deleteUser() {
		userRepository.deleteById((long) 3);
		List<User> users = (List<User>) userRepository.findAll();
		assertThat(users).hasSize(2);
	}
	
}
