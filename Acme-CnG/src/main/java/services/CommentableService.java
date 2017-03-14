
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentableRepository	commentableRepository;


	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Commentable> findAll() {
		Collection<Commentable> result;
		result = commentableRepository.findAll();
		return result;
	}

	public Commentable findOne(int commentableId) {
		Commentable result;
		result = commentableRepository.findOne(commentableId);
		return result;
	}

	public Commentable save(Commentable commentable) {
		return commentableRepository.save(commentable);
	}

	public void delete(Commentable commentable) {
		commentableRepository.delete(commentable);
	}

	// Other business methods ---------------------------
	public Commentable findCommentableById(int id) {
		Commentable result;

		result = commentableRepository.findCommentableById(id);

		return result;
	}

}
