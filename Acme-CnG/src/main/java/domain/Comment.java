
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Attributes --------------------------------------

	private String	title;
	private Date	postedMoment;
	private String	text;
	private Integer	stars;


	// Getters and Setters -----------------------------

	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPostedMoment() {
		return postedMoment;
	}
	public void setPostedMoment(Date postedMoment) {
		this.postedMoment = postedMoment;
	}

	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Range(min = 0, max = 5)
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}


	// Relationships -----------------------------------

	private Actor		actor;
	private Commentable	commentable;

	@Valid
	@ManyToOne(optional=false)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	@Valid
	@ManyToOne(optional=false)
	public Commentable getCommentable() {
		return commentable;
	}
	public void setCommentable(Commentable commentable) {
		this.commentable = commentable;
	}

}
