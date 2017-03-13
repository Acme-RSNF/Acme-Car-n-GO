
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

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

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	// Relationships -----------------------------------

}
