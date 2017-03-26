
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplyForRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplyFor;
import domain.Customer;

@Service
@Transactional
public class ApplyForService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplyForRepository	applyForRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public ApplyForService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ApplyFor create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		ApplyFor result;
		result = new ApplyFor();

		Customer customer;
		customer = customerService.findByPrincipal();

		result.setCustomer(customer);
		result.setStatus("PENDING");

		return result;
	}

	public Collection<ApplyFor> findAll() {
		Collection<ApplyFor> result;

		result = applyForRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ApplyFor findOne(int applyForId) {
		ApplyFor result;

		result = applyForRepository.findOne(applyForId);
		Assert.notNull(result);

		return result;
	}

	public ApplyFor save(ApplyFor applyFor) {

		Assert.notNull(applyFor);
		ApplyFor result;
		
		Collection<ApplyFor> applies = findByCreator();
		
		for(ApplyFor a : applies){
			Assert.isTrue(a.getDeal().getId()!=applyFor.getDeal().getId());
		}
		
		result = applyForRepository.save(applyFor);

		return result;
	}

	public ApplyFor save2(ApplyFor applyFor) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(applyFor);

		ApplyFor result;

		result = applyForRepository.save(applyFor);

		return result;
	}

	public void delete(ApplyFor applyFor) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(applyFor);
		Assert.isTrue(applyFor.getId() != 0);
		Assert.isTrue(applyFor.getStatus().equals("PENDING"));
		Assert.isTrue(applyFor.getCustomer().equals(customerService.findByPrincipal()));

		applyForRepository.delete(applyFor);
	}

	// Other business methods -------------------------------------------------

	public Collection<ApplyFor> findByCreator() {
		Collection<ApplyFor> result = new ArrayList<ApplyFor>();
		Customer customer;
		customer = customerService.findByPrincipal();
		result = applyForRepository.findByCreator(customer);
		return result;
	}
	
	public Collection<ApplyFor> findByDealCreator() {
		Collection<ApplyFor> result = new ArrayList<ApplyFor>();
		Customer customer;
		customer = customerService.findByPrincipal();
		result = applyForRepository.findByDealCreator(customer);
		return result;
	}

}
