package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer>{
	@Query("select r from Offer r where r.customer= ?1")
	Collection<Offer> findByCreator(Customer d);
}
