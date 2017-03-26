
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BannerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import forms.BannerForm;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BannerRepository	bannerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Banner create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Banner result;
		result = new Banner();
		result.setIsPrincipal(false);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = bannerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Banner findOne(int bannerId) {
		Banner result;

		result = bannerRepository.findOne(bannerId);
		Assert.notNull(result);

		return result;
	}

	public Banner save(Banner banner) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.notNull(banner);
		Banner result;
		result = bannerRepository.save(banner);

		return result;
	}

	public void delete(Banner banner) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);
		if (banner.getIsPrincipal() == false)
			bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

	public void makePrincipal(Banner banner) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Collection<Banner> aux = bannerRepository.findAll();
		for (Banner b : aux)
			b.setIsPrincipal(false);
		banner.setIsPrincipal(true);
	}

	public Banner bannerIsPrincipal() {
		Banner result;
		result = bannerRepository.bannerIsPrincipal();
		return result;
	}

	// Form methods ----------------------------------------------------------

	public BannerForm generateForm() {
		BannerForm result = new BannerForm();

		return result;
	}

	

	

	public Banner reconstruct(BannerForm bannerForm, BindingResult binding) {
		Banner result;
		if (bannerForm.getId() == 0)
			result = create();
		else {
			result = bannerRepository.findOne(bannerForm.getId());
			
		}
		result.setImage(bannerForm.getImage());
		result.setIsPrincipal(bannerForm.getIsPrincipal());
		validator.validate(result, binding);
		return result;
	}

	public BannerForm transform(Banner banner) {
		BannerForm result = generateForm();
		result.setId(banner.getId());
		result.setImage(banner.getImage());
		result.setIsPrincipal(banner.getIsPrincipal());
		return result;
	}

}
