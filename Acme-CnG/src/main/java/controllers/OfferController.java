
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfferService;
import domain.Customer;
import domain.Offer;

@Controller
@RequestMapping("/offer")
public class OfferController extends AbstractController {

	//Services-------------------------

	@Autowired
	private OfferService	offerService;


	//Constructor----------------------

	public OfferController() {
		super();
	}
	//Display-----------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int offerId) {
			ModelAndView result;
			Offer offer;
			
			offer = offerService.findOne(offerId);
			result=new ModelAndView("offer/display");
			result.addObject("offer", offer);
			result.addObject("comments", offer.getComments());
			result.addObject("requestURI", "offer/display.do");

			return result;
		}
	
	//List--------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Offer> offers = new ArrayList<Offer>();
		Collection<Offer> aux;
		aux = offerService.findAll();

		for (Offer o : aux)
			if (o.getBanned() == false)
				offers.add(o);

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("requestURI", "offer/list.do");

		return result;
	}

	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public ModelAndView listAdmin() {
		ModelAndView result;
		Collection<Offer> offers;
		offers = offerService.findAll();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("requestURI", "offer/listAdmin.do");

		return result;
	}

	@RequestMapping(value = "/banUnbanOffer", method = RequestMethod.GET)
	public ModelAndView banUnbanOffer(@RequestParam int offerId) {
		ModelAndView result;
		Offer o = offerService.findOne(offerId);
		try {
			offerService.banUnbanOffer(o);
			result = listAdmin();
		} catch (Throwable oops) {
			result = listAdmin();
			result.addObject("message", "master-page.commit.error");
		}

		return result;
	}

}
