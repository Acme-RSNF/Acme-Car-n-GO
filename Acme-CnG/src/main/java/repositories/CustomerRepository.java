
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id=?1")
	Customer findByUserAccountId(int id);

	//Dashboard

	@Query("select avg(c.deals.size) from Customer c")
	Double avgOfferRequestCustomer();

	@Query("select a.customer from ApplyFor a where a.status='ACCEPTED' group by a.customer having count(a)>=all(select count(a2) from ApplyFor a2 where a2.status='ACCEPTED' group by a2.customer)")
	Collection<Customer> customerApplyAccepted();

	@Query("select a.customer from ApplyFor a where a.status='DENIED' group by a.customer having count(a)>=all(select count(a2) from ApplyFor a2 where a2.status='DENIED' group by a2.customer)")
	Collection<Customer> customerApplyDenied();
}
