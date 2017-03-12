
package domain;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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


	public synchronized Customer getCustomer() {
		return customer;
	}
	public synchronized void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public synchronized Deal getDeal() {
		return deal;
	}
	public synchronized void setDeal(Deal deal) {
		this.deal = deal;
	}

}
