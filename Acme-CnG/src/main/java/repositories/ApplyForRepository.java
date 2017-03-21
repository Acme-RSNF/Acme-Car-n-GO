package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ApplyFor;
import domain.Customer;

@Repository
public interface ApplyForRepository extends JpaRepository<ApplyFor,Integer>{
	@Query("select r from ApplyFor r where r.customer= ?1")
	Collection<ApplyFor> findByCreator(Customer c);
	
	@Query("select r from ApplyFor r where r.deal.customer= ?1")
	Collection<ApplyFor> findByDealCreator(Customer c);
}
