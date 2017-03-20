package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Embeddable
@Access(AccessType.PROPERTY)
public class CommentForm {

	// Attributes ----------------------------------------------------
	
	private int commentableId;
	private String title;
	private String text;
	private int stars;
	
	public int getCommentableId() {
		return commentableId;
	}
	public void setCommentableId(int idCommentable) {
		this.commentableId = idCommentable;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Range(min=0, max=5)
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	
	
}