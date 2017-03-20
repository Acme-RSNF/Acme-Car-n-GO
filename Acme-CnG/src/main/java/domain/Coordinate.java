
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinate {

	// Constructor -------------------------------------

	public Coordinate() {
		super();
	}


	// Attributes --------------------------------------
	private String	latitude;
	private String	longitude;


	// Getters and Setters -----------------------------

	@Pattern(regexp = "^$|^(-)?(\\d{1,2})(\\.)?(\\d{1,6})$")
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Pattern(regexp = "^$|^(-)?(\\d{1,3})(\\.)?(\\d{1,6})$")
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	// Relationships -----------------------------------

}
