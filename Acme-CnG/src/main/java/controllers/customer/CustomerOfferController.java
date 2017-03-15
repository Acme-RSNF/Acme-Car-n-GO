
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import controllers.AbstractController;
import domain.Offer;

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

}
