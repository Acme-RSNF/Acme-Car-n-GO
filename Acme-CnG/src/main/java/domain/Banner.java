
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
@Entity
@Access(AccessType.PROPERTY)
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
