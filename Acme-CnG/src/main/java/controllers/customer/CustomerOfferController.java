
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import controllers.AbstractController;
import domain.Offer;
import forms.OfferForm;

@Controller
@RequestMapping("/customer/offer")
public class CustomerOfferController extends AbstractController {

	//Services-------------------------

	@Autowired
	private OfferService	offerService;


	//Constructor----------------------

	public CustomerOfferController() {
		super();
	}

	//List--------------------------

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Offer> offers;

		offers = offerService.findByCreator();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("requestURI", "offer/list.do");

		return result;
	}

	// Create -------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		OfferForm offerForm = offerService.generate();

		result = new ModelAndView("offer/create");
		result.addObject("offerForm", offerForm);
		result.addObject("requestURI", "customer/offer/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid OfferForm offerForm, BindingResult binding) {

		ModelAndView result;
		Offer offer;

		if (binding.hasErrors()) {
			result = createEditModelAndView(offerForm);
		} else {
			try {
				offer = offerService.reconstruct(offerForm, binding);
				offerService.save2(offer);
				result = new ModelAndView("redirect:/offer/list.do");
			} catch (Throwable oops) {
				String msgCode = "offer.error";
				result = createEditModelAndView(offerForm, msgCode);
			}
		}

		return result;

	}

	// Ancillary methods ---------------------------------------

	protected ModelAndView createEditModelAndView(OfferForm offerForm) {
		ModelAndView result;

		result = createEditModelAndView(offerForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(OfferForm offerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("offer/create");
		result.addObject("offerForm", offerForm);
		result.addObject("message", message);

		return result;
	}

}
