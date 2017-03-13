
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Attributes --------------------------------------

	// Getters and Setters -----------------------------

	// Relationships -----------------------------------

	private Collection<Deal>		deals;
	private Collection<ApplyFor>	applies;

	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<Deal> getDeals() {
		return deals;
	}
	public void setDeals(Collection<Deal> deals) {
		this.deals = deals;
	}
	@Valid
	@OneToMany(mappedBy="customer")
	public Collection<ApplyFor> getApplies() {
		return applies;
	}
	public void setApplies(Collection<ApplyFor> applies) {
		this.applies = applies;
	}

}
