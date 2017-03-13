
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplyFor;
import domain.Customer;

@Service
@Transactional
public class ApplyForService {

	// Managed repository -----------------------------------------------------
/*
	@Autowired
	private ApplyForRepository	applyForRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ApplyForService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ApplyFor create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		ApplyFor result;
		result = new ApplyFor();

		Customer customer;
		customer = customerService.findByPrincipal();

		result.setCustomer(customer);

		return result;
	}

	public Collection<ApplyFor> findAll() {
		Collection<ApplyFor> result;

		result = this.applyForRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ApplyFor findOne(final int applyForId) {
		ApplyFor result;

		result = this.applyForRepository.findOne(applyForId);
		Assert.notNull(result);

		return result;
	}

	public Collection<ApplyFor> findByPrincipal() {
		Collection<ApplyFor> result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.applyForRepository.findByUserAccount(userAccount);

		return result;
	}

	public ApplyFor save(final ApplyFor applyFor) {

		Assert.notNull(applyFor);
		ApplyFor result;
		result = this.applyForRepository.save(applyFor);

		return result;
	}

	public ApplyFor save2(final ApplyFor applyFor) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(applyFor);

		ApplyFor result;

		result = this.applyForRepository.save(applyFor);

		return result;
	}

	public void delete(final ApplyFor applyFor) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(applyFor);
		Assert.isTrue(applyFor.getId() != 0);

		this.applyForRepository.delete(applyFor);
	}

	// Other business methods -------------------------------------------------
*/
}
