
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Banner extends DomainEntity {

	// Attributes --------------------------------------

	private String	image;


	// Getters and Setters -----------------------------

	@URL
	@NotBlank
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	// Relationships -----------------------------------

}
