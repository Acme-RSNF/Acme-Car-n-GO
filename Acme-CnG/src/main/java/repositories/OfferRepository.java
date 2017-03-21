
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select r from Offer r where r.customer= ?1")
	Collection<Offer> findByCreator(Customer d);

	@Query("select o from Offer o where o.title like %?1% or o.description like %?1% or o.origin like %?1% or o.destination like %?1%")
	Collection<Offer> findByKey(String key);
}
