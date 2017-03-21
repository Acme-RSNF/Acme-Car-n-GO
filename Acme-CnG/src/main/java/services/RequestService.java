
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplyFor;
import domain.Comment;
import domain.Customer;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private Validator			validator;


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
		result.setBanned(false);
		result.setIsCommentable(true);

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

	public void banUnbanRequest(Request request) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		if (request.getBanned())
			request.setBanned(false);
		else
			request.setBanned(true);
	}

	// Form methods -------------------------------------------------------------

	public RequestForm generate() {
		RequestForm result = new RequestForm();

		return result;
	}

	public Request reconstruct(RequestForm requestForm, BindingResult binding) {
		Request result;
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<ApplyFor> applies = new ArrayList<ApplyFor>();
		Customer customer = customerService.findByPrincipal();

		result = create();

		result.setTitle(requestForm.getTitle());
		result.setDescription(requestForm.getDescription());
		result.setApplies(applies);
		result.setComments(comments);
		result.setDestination(requestForm.getDestination());
		result.setOrigin(requestForm.getOrigin());
		result.setDestinationCoordinate(requestForm.getDestinationCoordinate());
		result.setOriginCoordinate(requestForm.getOriginCoordinate());
		result.setMoment(requestForm.getMoment());
		result.setCustomer(customer);

		validator.validate(result, binding);

		return result;
	}

	public Collection<Request> findByKey(String key) {
		Collection<Request> aux = requestRepository.findByKey(key);
		Collection<Request> result = new ArrayList<Request>();
		for (Request r : aux)
			if (r.getBanned() == false)
				result.add(r);

		return result;

	}

}
