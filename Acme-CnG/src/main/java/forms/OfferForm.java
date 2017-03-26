
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Coordinate;

@Embeddable
@Access(AccessType.PROPERTY)
public class OfferForm {

	// Attributes ----------------------------------------------------

	private int			offerId;
	private String		title;
	private String		description;
	private Date		moment;
	private String		origin;
	private Coordinate	originCoordinate;
	private String		destination;
	private Coordinate	destinationCoordinate;


	// Constructor --------------------------------------------------

	public OfferForm() {
		super();
	}

	// Getter y Setter ------------------

	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Valid
	public Coordinate getOriginCoordinate() {
		return originCoordinate;
	}
	public void setOriginCoordinate(Coordinate originCoordinate) {
		this.originCoordinate = originCoordinate;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Valid
	public Coordinate getDestinationCoordinate() {
		return destinationCoordinate;
	}
	public void setDestinationCoordinate(Coordinate destinationCoordinate) {
		this.destinationCoordinate = destinationCoordinate;
	}

}
