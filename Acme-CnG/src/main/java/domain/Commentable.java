
package domain;

import java.util.Collection;

import javax.persistence.OneToMany;
import javax.validation.Valid;

public class Commentable extends DomainEntity {

	// Attributes --------------------------------------

	private Boolean	isCommentable;


	// Getters and Setters -----------------------------

	public Boolean getIsCommentable() {
		return isCommentable;
	}
	public void setIsCommentable(Boolean isCommentable) {
		this.isCommentable = isCommentable;
	}


	// Relationships -----------------------------------

	private Collection<Comment>	comments;

	@Valid
	@OneToMany(mappedBy="commentable")
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
