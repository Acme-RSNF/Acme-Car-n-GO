
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.customer= ?1")
	Collection<Request> findByCreator(Customer c);

	@Query("select r from Request r where r.title like %?1% or r.description like %?1% or r.origin like %?1% or r.destination like %?1%")
	Collection<Request> findByKey(String key);
}
