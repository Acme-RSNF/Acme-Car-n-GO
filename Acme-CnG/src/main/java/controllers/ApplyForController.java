/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplyForService;
import services.DealService;
import domain.ApplyFor;
import domain.Deal;
import forms.CustomerForm;

@Controller
@RequestMapping("/applyFor")
public class ApplyForController extends AbstractController {

	//Services-------------------------

	@Autowired
	private ApplyForService	applyForService;
	
	@Autowired
	private DealService	dealService;


	// Constructors -----------------------------------------------------------

	public ApplyForController() {
		super();
	}

	// Display
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display() {
			ModelAndView result;
			Collection<ApplyFor> apply;
			Collection<ApplyFor> applyD;
			
			apply = applyForService.findByCreator();
			applyD = applyForService.findByDealCreator();
			
			result=new ModelAndView("applyFor/display");
			result.addObject("applyFor", apply);
			result.addObject("applyForDeal", applyD);
			result.addObject("requestURI", "applyFor/display.do");

			return result;
		}
	
	// Creation ------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int dealId) {
		ModelAndView result;
		ApplyFor applyFor = applyForService.create();
		Deal deal = dealService.findOne(dealId);
		applyFor.setDeal(deal);
		
		applyForService.save(applyFor);
		
		result = display();

		return result;
	}


	// Ancillary methods ---------------------------------------------------

	protected ModelAndView createEditModelAndView(CustomerForm customerForm) {
		ModelAndView result;

		result = createEditModelAndView(customerForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(CustomerForm customerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("customer/register");
		result.addObject("customerForm", customerForm);
		result.addObject("message", message);

		return result;
	}
}
