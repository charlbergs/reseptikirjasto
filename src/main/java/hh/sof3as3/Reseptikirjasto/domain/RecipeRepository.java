package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	List<Recipe> findByAuthor(User user);

}
