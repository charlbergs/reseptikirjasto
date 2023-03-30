package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;

@Controller
public class CategoryController {
	
	// repositoriot
	@Autowired
	private CategoryRepository categoryRepository;
	
	// todo: kategorioiden lisäys, muokkaus, poisto
	
	// tulostustesti
	@GetMapping("/categorylist")
	public String getIndex(Model model) {
		// luodaan lista ja lisätään testikategoriat
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		// välitetään templatelle
		model.addAttribute("categories", categories);
		return "categorylist";
	}
}
