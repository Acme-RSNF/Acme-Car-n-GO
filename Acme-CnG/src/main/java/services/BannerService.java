
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Customer;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
/*
	@Autowired
	private BannerRepository	bannerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Banner create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Banner result;
		result = new Banner();

		Customer customer;
		customer = customerService.findByPrincipal();

		result.setCustomer(customer);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = bannerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Banner findOne(final int bannerId) {
		Banner result;

		result = bannerRepository.findOne(bannerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Banner> findByPrincipal() {
		Collection<Banner> result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = bannerRepository.findByUserAccount(userAccount);

		return result;
	}

	public Banner save(final Banner banner) {

		Assert.notNull(banner);
		Banner result;
		result = bannerRepository.save(banner);

		return result;
	}

	public Banner save2(final Banner banner) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(banner);

		Banner result;

		result = bannerRepository.save(banner);

		return result;
	}

	public void delete(final Banner banner) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------
*/
}
