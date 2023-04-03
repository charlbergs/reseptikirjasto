package hh.sof3as3.Reseptikirjasto.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByRecipe(Recipe recipe); // hakee ja palauttaa listalle kaikki reseptin kommentit
}
