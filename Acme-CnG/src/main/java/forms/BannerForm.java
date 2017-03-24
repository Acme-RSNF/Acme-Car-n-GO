
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class BannerForm {

	private int		id;
	private String	image;
	private boolean isPrincipal;

	// Constructor --------------------------------------------------

	

	public BannerForm() {
		super();
	}

	// Getters and Setters --------------------------------

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@NotNull
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public boolean getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}
}
