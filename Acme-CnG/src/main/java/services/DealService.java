
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DealRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Deal;

@Service
@Transactional
public class DealService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DealRepository	dealRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public DealService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Deal create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Deal result;

		result = new Deal();

		return result;
	}

	public Collection<Deal> findAll() {
		Collection<Deal> result;

		result = dealRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Deal findOne(int dealId) {
		Deal result;

		result = dealRepository.findOne(dealId);
		Assert.notNull(result);

		return result;
	}

	public Deal save(Deal deal) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(deal);

		Deal result;

		result = dealRepository.save(deal);

		return result;
	}

	public void delete(Deal deal) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(deal);
		Assert.isTrue(deal.getId() != 0);

		dealRepository.delete(deal);
	}

	// Other business methods -------------------------------------------------

	public Collection<Deal> findByCreator() {
		Collection<Deal> result = new ArrayList<Deal>();
		Customer customer;
		customer = customerService.findByPrincipal();
		result = dealRepository.findByCreator(customer);
		return result;
	}

	// Dashboard Services -----------------------

	public Double ratioOfferVsRequest() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Double result;
		result = dealRepository.ratioOfferVsRequest();
		return result;
	}

	public Double avgApplyDeal() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Double result;
		result = dealRepository.avgApplyDeal();
		return result;
	}
}
