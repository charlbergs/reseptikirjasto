package hh.sof3as3.Reseptikirjasto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof3as3.Reseptikirjasto.domain.Category;
import hh.sof3as3.Reseptikirjasto.domain.CategoryRepository;
import hh.sof3as3.Reseptikirjasto.domain.Recipe;
import hh.sof3as3.Reseptikirjasto.domain.RecipeRepository;

@SpringBootApplication
public class ReseptikirjastoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReseptikirjastoApplication.class, args);
	}
	
	// luodaan commandlinerunnerilla tietokantaan testidataa
	@Bean
	public CommandLineRunner demo (RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			
			// testikategoriat
			Category categ1 = new Category("Jälkiruoat", "#2596be");
			Category categ2 = new Category("Aamupalat", "#56ba47");
			Category categ3 = new Category("Keitot", "#be4d25");
			// tallennetaan  repositorioon
			categoryRepository.save(categ1);
			categoryRepository.save(categ2);
			categoryRepository.save(categ3);
			
			// testireseptit
			Recipe rec1 = new Recipe(
				"Munakas", 1, 15,
				"- 2 kananmunaa \n"
				+ "- loraus maitoa \n"
				+ "- suolaa, pippuria \n"
				+ "- öljyä paistamiseen", 
				"1. Sekoita ainekset. \n"
				+ "2. Paista pannulla. \n"
				+ "3. Syö."
			);
			Recipe rec2 = new Recipe(
				"Kasvissosekeitto", 4, 30,
				"500 g porkkanaa\n"
				+ "2-3 perunaa\n"
				+ "1 sipuli\n"
				+ "loraus öljyä\n"
				+ "0,7 l vettä\n"
				+ "1 kasvisliemikuutio\n"
				+ "1 dl kermaa tai kaurakermaa\n"
				+ "0,5 tl inkivääriä\n"
				+ "ripaus chilijauhetta\n", 
				"1. Kuori ja paloittele porkkanat ja perunat. Hienonna sipuli. \n"
				+ "2. Kuullota kasvikset öljyssä kattilassa. \n"
				+ "3. Lisää vesi ja liemikuutio. Keitä kunnes kasvikset pehmenevät.\n"
				+ "4. Soseuta sileäksi sauvasekoittimella. Lisää kerma, ja mausta inkiväärillä ja chilillä." 
			);
			Recipe rec3 = new Recipe(
				"Mustikkapiirakka kermaviilillä", 8, 45,
				"Pohja:\n"
				+ "- 100 g voita\n"
				+ "- 0,75 dl sokeria\n"
				+ "- 1 kananmuna\n"
				+ "- 2,5 dl vehnäjauhoja\n"
				+ "- 1 tl leivinjauhetta\n"
				+ "\n"
				+ "Täyte:\n"
				+ "- 200-300 g mustikoita\n"
				+ "- 1 kananmuna\n"
				+ "- 1 prk kermaviiliä\n"
				+ "- 0,5 dl sokeria\n"
				+ "- 1 tl vaniljasokeria\n",
				"1. Sekoita sokeri ja sulatettu voi. Lisää joukkoon kananmuna ja vaahdota.\n"
				+ "2. Sekoita erillisessä kulhossa jauhot ja leivinjauhe.\n"
				+ "3. Lisää jauhot munasokerivoi-seokseen, ja painele taikina voideltuun piirakkavuokaan.\n"
				+ "4. Sekoita täytettä varten kananmuna, kermaviili ja sokerit.\n"
				+ "5. Ripottele mustikat vuokaan, ja valuta sitten kermaviiliseos niiden päälle.\n"
				+ "6. Laita uuniin ja paista alatasolla 200 asteessa n. 30 minuuttia."
			);
			Recipe rec4 = new Recipe(
				"Letut", 10, 180, 
				"2 kananmunaa\n"
				+ "0,5 l maitoa\n"
				+ "3 dl vehnäjauhoja\n"
				+ "1 rkl sokeria\n"
				+ "0,5 tl suolaa\n"
				+ "voita tai margariinia paistamiseen\n"
				+ "\n"
				+ "täytteeksi esim. hilloa ja kermavaahtoa\n", 
				"1. Vatkaa munat ja n. puolet maidosta kulhossa. Lisää jauhot, sokeri ja suola koko ajan sekoittaen. \n"
				+ "2. Lisää loput maidosta. Anna taikinan turvota puoli tuntia huoneenlämmössä. \n"
				+ "4. Kuumenna pannu, ja paista letut rasvassa molemmilta puolilta. Mikäli letut ruskistuvat liikaa, vähennä lämpöä. Jos taas letut jäävät keskeltä paksuiksi ja vaaleiksi, kannattaa taikinaa annostella vähemmän.\n"
				+ "5. Tarjoa haluamasi täytteen kanssa. Jos haluat täyttää letut suolaisella täytteellä, voi taikinasta myös jättää sokerin pois.\n"
			);
			// tallennetaan repositorioon
			recipeRepository.save(rec1);
			recipeRepository.save(rec2);
			recipeRepository.save(rec3);
			recipeRepository.save(rec4);
			
	
		};
	}

}
