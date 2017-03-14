
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select c from Commentable c where c.id=?1 and c.isCommentable=true")
	Commentable findCommentableById(int id);

	//Dashboard

	@Query("select avg(c.comments.size) from Commentable c")
	Double avgComment();
}
