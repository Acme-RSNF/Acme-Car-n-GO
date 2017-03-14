
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BannerRepository	bannerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BannerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Banner create() {

		Banner result;
		result = new Banner();

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

		Assert.notNull(banner);
		Banner result;
		result = bannerRepository.save(banner);

		return result;
	}

	public void delete(Banner banner) {

		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------

}
