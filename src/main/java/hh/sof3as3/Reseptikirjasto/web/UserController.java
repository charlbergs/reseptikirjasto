package hh.sof3as3.Reseptikirjasto.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.SignupForm;
import hh.sof3as3.Reseptikirjasto.domain.User;
import hh.sof3as3.Reseptikirjasto.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
@EnableMethodSecurity(securedEnabled = true)
public class UserController {
	
	// repositoriot
	@Autowired
	private UserRepository userRepository;
	
	// hakee uuden käyttäjän rekisteröintilomakkeen
	@GetMapping("/signup")
	@PreAuthorize("!hasAnyAuthority('Admin', 'USER')") // vain kirjautumaton käyttäjä
	public String addUser(Model model) {
    	model.addAttribute("signupform", new SignupForm());
        return "signupform";
    }	
	
	// luo uuden käyttäjän rekisteröintilomakkeelta
	@PostMapping("/saveuser")
	@PreAuthorize("!hasAnyAuthority('Admin', 'USER')") // vain kirjautumaton käyttäjä
	public String saveUser(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			// tarkistetaan että syötetyt password-kentät ovat samat
			// ja encryptataan salasana
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {	
	    		String password = signupForm.getPassword();
		    	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		    	String passwordHash = bcrypt.encode(password);
	
		    	// luodaan uusi user-olio
		    	User newUser = new User();
		    	newUser.setPasswordHash(passwordHash);
		    	newUser.setUsername(signupForm.getUsername());
		    	newUser.setRole("USER");
		    	
		    	// tarkistetaan ettei tietokannassa ole samannimistä käyttäjää
		    	// ja tallennetaan käyttäjä
		    	if (userRepository.findByUsername(signupForm.getUsername()) == null) {
		    		userRepository.save(newUser);
		    	}
		    	// jos käyttäjä jo tietokannassa, error ja palataan formille
		    	else {
	    			bindingResult.rejectValue("username", "err.usernameNotUnique", "Käyttäjänimi on jo käytössä.");    	
	    			return "signupform";		    		
		    	}
    		}
    		// jos salasanat ei samat, error ja palataan formille
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passwordCheck", "Salasana ei täsmää.");    	
    			return "signupform";
    		}
    	}
    	else {
    		// jos lähetetyissä tiedoissa virheitä, palataan formille
    		return "signupform";
    	}
    	return "redirect:/login"; // jos kaikki onnistuu, siirrytään loginiin
	}

}
