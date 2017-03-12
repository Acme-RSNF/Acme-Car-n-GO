
package domain;

import java.util.Collection;

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


	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
