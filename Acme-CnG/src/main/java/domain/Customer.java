
package domain;

import java.util.Collection;

public class Customer extends Actor {

	// Attributes --------------------------------------

	// Getters and Setters -----------------------------

	// Relationships -----------------------------------

	private Collection<Deal>		deals;
	private Collection<ApplyFor>	applies;


	public synchronized Collection<Deal> getDeals() {
		return deals;
	}
	public synchronized void setDeals(Collection<Deal> deals) {
		this.deals = deals;
	}

	public synchronized Collection<ApplyFor> getApplies() {
		return applies;
	}
	public synchronized void setApplies(Collection<ApplyFor> applies) {
		this.applies = applies;
	}

}
