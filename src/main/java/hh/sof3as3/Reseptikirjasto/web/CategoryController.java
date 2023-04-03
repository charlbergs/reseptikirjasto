package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;

@Controller
public class CategoryController {
	
	// repositoriot
	@Autowired
	private CategoryRepository categoryRepository;
	
	// todo: kategorioiden muokkaus & poisto
	
	// kategorianäkymä: listaus ja lisäyslomake
	@GetMapping("/categorylist")
	public String getCategories(Model model) {
		// haetaan kategoriat listalle ja välitetään templatelle
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		model.addAttribute("categories", categories);
		// välitetään tyhjä kategoriaolio templatelle kategorian lisäystä varten
		model.addAttribute("newCategory", new Category());
		return "categorylist";
	}
	
	// kategorian lisäys: post
	@PostMapping("/addcategory")
	public String addCategory(Category category) {
		categoryRepository.save(category);
		return "redirect:/categorylist";
	}
	
}
