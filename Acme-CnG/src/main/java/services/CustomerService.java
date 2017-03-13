
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import domain.ApplyFor;
import domain.Comment;
import domain.Customer;
import domain.Deal;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
/*
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {

		UserAccount userAccount = new UserAccount();
		List<Authority> authorities = new ArrayList<Authority>();
		Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		authorities.add(a);
		userAccount.addAuthority(a);
		Customer result = new Customer();
		result.setUserAccount(userAccount);
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<Comment> writtenComments = new ArrayList<Comment>();
		Collection<ApplyFor> applies = new ArrayList<ApplyFor>();
		Collection<Deal> deals = new ArrayList<Deal>();
		result.setComments(comments);
		result.setWrittenComments(writtenComments);
		result.setIsCommentable(true);
		result.setApplies(applies);
		result.setDeals(deals);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Customer findOne(int customerId) {
		Customer result;
		result = customerRepository.findOne(customerId);
		return result;
	}

	public Customer save(Customer customer) {
		Assert.notNull(customer);

		String password = customer.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		customer.getUserAccount().setPassword(md5);

		//Assert.isTrue(check(customer.getCreditCard()));

		Customer result = customerRepository.save(customer);

		return result;
	}

	public Customer save2(Customer customer) {
		Customer result;

		result = customerRepository.save(customer);

		return result;
	}

	public void delete(Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		customerRepository.delete(customer);
	}
*/
}
