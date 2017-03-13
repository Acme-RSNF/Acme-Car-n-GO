
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
@Entity
@Access(AccessType.PROPERTY)
public class ApplyFor extends DomainEntity {

	// Attributes --------------------------------------

	private String	status;


	// Getters and Setters -----------------------------

	@NotBlank
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^DENIED$")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	// Relationships -----------------------------------

	private Customer	customer;
	private Deal		deal;

	@Valid
	@ManyToOne(optional=false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Valid
	@ManyToOne(optional=false)
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}

}
