
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplyFor;
import domain.Comment;
import domain.Customer;
import domain.Deal;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator			validator;


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
		
		if(customer.getId()!=0){
			Assert.isTrue(findByPrincipal().getId()==customer.getId());
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("CUSTOMER");
			Assert.isTrue(userAccount.getAuthorities().contains(au));
		}
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
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		customerRepository.delete(customer);
	}

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		assert userAccount != null;
		result = findByUserAccount(userAccount);
		assert result != null;

		return result;
	}

	public Customer findByUserAccount(UserAccount userAccount) {
		assert userAccount != null;

		Customer result;

		result = customerRepository.findByUserAccountId(userAccount.getId());
		assert result != null;

		return result;
	}

	//Dashboard Services ------

	public Double avgOfferRequestCustomer() {
		Double result;
		result = customerRepository.avgOfferRequestCustomer();
		return result;
	}

	public Collection<Customer> customerApplyAccepted() {
		Collection<Customer> result;
		result = customerRepository.customerApplyAccepted();
		return result;
	}

	public Collection<Customer> customerApplyDenied() {
		Collection<Customer> result;
		result = customerRepository.customerApplyDenied();
		return result;
	}

	// Form methods ------------------------------------------------

	public CustomerForm generateForm() {
		CustomerForm result;

		result = new CustomerForm();
		return result;
	}

	public CustomerForm generateForm(Customer customer) {
		CustomerForm result;

		result = new CustomerForm();

		result.setId(customer.getId());
		result.setUsername(customer.getUserAccount().getUsername());
		result.setPassword(customer.getUserAccount().getPassword());
		result.setPassword2(customer.getUserAccount().getPassword());
		result.setName(customer.getName());
		result.setAgreed(true);
		result.setSurname(customer.getSurname());
		result.setPhone(customer.getPhone());
		result.setEmail(customer.getEmail());

		return result;
	}

	public Customer reconstruct(CustomerForm customerForm, BindingResult binding) {

		Customer result = create();

		String password;
		password = customerForm.getPassword();

		Assert.isTrue(customerForm.getPassword2().equals(password), "notEqualPassword");
		Assert.isTrue(customerForm.getAgreed(), "agreedNotAccepted");

		UserAccount userAccount;
		userAccount = new UserAccount();
		userAccount.setUsername(customerForm.getUsername());
		userAccount.setPassword(password);

		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);

		result.setName(customerForm.getName());
		result.setSurname(customerForm.getSurname());
		result.setEmail(customerForm.getEmail());
		result.setPhone(customerForm.getPhone());

		validator.validate(result, binding);

		return result;

	}

	public Customer reconstructEditPersonalData(CustomerForm customerForm, BindingResult binding) {
		Customer result;

		result = customerRepository.findOne(customerForm.getId());

		result.setName(customerForm.getName());
		result.setSurname(customerForm.getSurname());
		result.setEmail(customerForm.getEmail());
		result.setPhone(customerForm.getPhone());

		validator.validate(result, binding);

		return result;
	}

}
