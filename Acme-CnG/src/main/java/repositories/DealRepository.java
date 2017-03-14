
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Deal;

@Repository
public interface DealRepository extends JpaRepository<Deal, Integer> {

	@Query("select r from Deal r where r.customer= ?1")
	Collection<Deal> findByCreator(Customer c);

	//Dashboard

	@Query("select 1.0*(select count(o) from Offer o)/count(r) from Request r")
	Double ratioOfferVsRequest();

	@Query("select avg(d.applies.size) from Deal d")
	Double avgApplyDeal();
}
