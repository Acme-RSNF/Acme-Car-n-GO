
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OfferRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplyFor;
import domain.Comment;
import domain.Customer;
import domain.Offer;
import forms.OfferForm;

@Service
@Transactional
public class OfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OfferRepository	offerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private Validator		validator;


	// Constructors -----------------------------------------------------------

	public OfferService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Offer create() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Offer result;
		result = new Offer();

		Customer customer;
		customer = customerService.findByPrincipal();

		result.setCustomer(customer);
		result.setBanned(false);
		result.setIsCommentable(true);

		return result;
	}

	public Collection<Offer> findAll() {
		Collection<Offer> result;

		result = offerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Offer findOne(int offerId) {
		Offer result;

		result = offerRepository.findOne(offerId);
		Assert.notNull(result);

		return result;
	}

	public Offer save(Offer offer) {

		Assert.notNull(offer);
		Offer result;
		result = offerRepository.save(offer);

		return result;
	}

	public Offer save2(Offer offer) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(offer);

		Offer result;

		result = offerRepository.save(offer);

		return result;
	}

	public void delete(Offer offer) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		Assert.notNull(offer);
		Assert.isTrue(offer.getId() != 0);

		offerRepository.delete(offer);
	}

	// Other business methods -------------------------------------------------

	public Collection<Offer> findByCreator() {
		Collection<Offer> result = new ArrayList<Offer>();
		Customer customer;
		customer = customerService.findByPrincipal();
		result = offerRepository.findByCreator(customer);
		return result;
	}

	public void banUnbanOffer(Offer offer) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));

		if (offer.getBanned())
			offer.setBanned(false);
		else
			offer.setBanned(true);
	}

	// Form methods -------------------------------------------------------------

	public OfferForm generate() {
		OfferForm result = new OfferForm();

		return result;
	}

	public Offer reconstruct(OfferForm offerForm, BindingResult binding) {
		Offer result;
		Collection<Comment> comments = new ArrayList<Comment>();
		Collection<ApplyFor> applies = new ArrayList<ApplyFor>();
		Customer customer = customerService.findByPrincipal();

		result = create();

		result.setTitle(offerForm.getTitle());
		result.setDescription(offerForm.getDescription());
		result.setApplies(applies);
		result.setComments(comments);
		result.setDestination(offerForm.getDestination());
		result.setOrigin(offerForm.getOrigin());
		result.setDestinationCoordinate(offerForm.getDestinationCoordinate());
		result.setOriginCoordinate(offerForm.getOriginCoordinate());
		result.setMoment(offerForm.getMoment());
		result.setCustomer(customer);

		validator.validate(result, binding);

		return result;
	}

}
