
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Request create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Request result;
		result = new Request();

		Customer customer;
		customer = customerService.findByPrincipal();

		result.setCustomer(customer);

		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = requestRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Request findOne(int requestId) {
		Request result;

		result = requestRepository.findOne(requestId);
		Assert.notNull(result);

		return result;
	}

	public Request save(Request request) {

		Assert.notNull(request);
		Request result;
		result = requestRepository.save(request);

		return result;
	}

	public Request save2(Request request) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(request);

		Request result;

		result = requestRepository.save(request);

		return result;
	}

	public void delete(Request request) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		requestRepository.delete(request);
	}

	// Other business methods -------------------------------------------------

	public Collection<Request> findByCreator() {
		Collection<Request> result = new ArrayList<Request>();
		Customer customer;
		customer = customerService.findByPrincipal();
		result = requestRepository.findByCreator(customer);
		return result;
	}

}
