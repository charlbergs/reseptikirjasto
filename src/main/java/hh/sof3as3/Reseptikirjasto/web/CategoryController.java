package hh.sof3as3.Reseptikirjasto.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;
import jakarta.validation.Valid;

@Controller
@EnableMethodSecurity(securedEnabled = true)
public class CategoryController {
	
	// repositoriot
	@Autowired
	private CategoryRepository categoryRepository;
	
	// kategorianäkymä, GET: listaus ja lisäyslomake samassa
	@GetMapping("/categorylist")
	@PreAuthorize("hasAuthority('ADMIN')") // vain admin
	public String getCategories(Model model) {
		// haetaan kategoriat listalle ja välitetään templatelle
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		model.addAttribute("categories", categories);
		// välitetään tyhjä kategoriaolio templatelle kategorian lisäystä varten
		model.addAttribute("newCategory", new Category());
		return "categorylist";
	}
	
	// kategorian lisäys, POST
	@PostMapping("/addcategory")
	@PreAuthorize("hasAuthority('ADMIN')") // vain admin
	public String addCategory(@Valid @ModelAttribute("newCategory") Category category, BindingResult bindingResult, Model model) {
		// tarkistetaan validointi: jos virheitä, palataan takaisin lomakkeen täyttöön
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll()); // välitetään kategorialistaus uudelleen, muuten taulukko tyhjä
			return "categorylist";
		}
		// jos ei virheitä, tallennetaan ja siirrytään takaisin listaukseen
		categoryRepository.save(category);
		return "redirect:/categorylist";
	}
	
	// kategorian muokkaus, GET: kutsuu erillistä muokkauslomaketta
	@GetMapping("/editcategory/{id}")
	@PreAuthorize("hasAuthority('ADMIN')") // vain admin
	public String editCategory(@PathVariable("id") Long id, Model model) {
		model.addAttribute("category", categoryRepository.findById(id).get());
		return "categoryform";
	}
	
	// kategorian muokkaus, POST
	@PostMapping("/savecategory")
	@PreAuthorize("hasAuthority('ADMIN')") // vain admin
	public String saveCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
		// validointi: jos virheitä, palataan takaisin lomakkeen täyttöön
		if (bindingResult.hasErrors()) {
			return "categoryform";
		}
		// jos ei virheitä, tallennetaan ja siirrytään takaisin listaukseen
		categoryRepository.save(category);
		return "redirect:/categorylist";
	}
	
	// kategorian poistaminen, GET
	@GetMapping("/deletecategory/{id}")
	@PreAuthorize("hasAuthority('ADMIN')") // vain admin
	public String deleteCategory(@PathVariable("id") Long id) {
		categoryRepository.deleteById(id);
		return "redirect:/categorylist";
	}
	
}
