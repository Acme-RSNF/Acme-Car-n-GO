
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Access(AccessType.PROPERTY)
public class Deal extends Commentable {

	// Attributes --------------------------------------

	private String		title;
	private String		description;
	private Date		moment;
	private String		origin;
	private Coordinate	originCoordinate;
	private String		destination;
	private Coordinate	destinationCoordinate;
	private Boolean		banned;


	// Getters and Setters -----------------------------

	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	 @AttributeOverrides({
		 @AttributeOverride(name = "latitude", column = @Column(name = "originLatitude")),
		 @AttributeOverride(name = "longitude", column = @Column(name = "originLongitude")),
		 })
	 public Coordinate getOriginCoordinate() {
		return originCoordinate;
	}
	public void setOriginCoordinate(Coordinate originCoordinate) {
		this.originCoordinate = originCoordinate;
	}

	@NotBlank
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "destinationLatitude")),
		@AttributeOverride(name = "longitude", column = @Column(name = "destinationLongitude")),
		})
	public Coordinate getDestinationCoordinate() {
		return destinationCoordinate;
	}
	public void setDestinationCoordinate(Coordinate destinationCoordinate) {
		this.destinationCoordinate = destinationCoordinate;
	}

	public Boolean getBanned() {
		return banned;
	}
	public void setBanned(Boolean banned) {
		this.banned = banned;
	}


	// Relationships -----------------------------------

	private Customer				customer;
	private Collection<ApplyFor>	applies;

	@Valid
	@ManyToOne(optional=false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Valid
	@OneToMany(mappedBy="deal")
	public Collection<ApplyFor> getApplies() {
		return applies;
	}
	public void setApplies(Collection<ApplyFor> applies) {
		this.applies = applies;
	}

}
