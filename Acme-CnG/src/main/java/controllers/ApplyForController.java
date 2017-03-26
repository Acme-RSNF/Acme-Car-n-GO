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
		Deal deal = dealService.findOne(dealId);
	
		ApplyFor applyFor = applyForService.create();
		applyFor.setDeal(deal);
		applyForService.save(applyFor);
		result = display();

		return result;
	}

	// Accept -------------------------------------------------------------
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int applyForId) {
		ModelAndView result;
		Deal deal;
		Collection<ApplyFor> applies;
		
		ApplyFor applyFor = applyForService.findOne(applyForId);
		applyFor.setStatus("ACCEPTED");
		applyForService.save(applyFor);
		
		deal = dealService.findOne(applyFor.getDeal().getId());
		applies = deal.getApplies();
		
		for(ApplyFor a : applies){
			if(a.getStatus().equals("PENDING")){
				a.setStatus("DENIED");
				applyForService.save(a);
			}
		}
				
		result = display();

		return result;
	}
	
	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(@RequestParam int applyForId) {
		ModelAndView result;
		
		ApplyFor applyFor = applyForService.findOne(applyForId);
		applyFor.setStatus("DENIED");
		applyForService.save(applyFor);
		
		result = display();

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int id) {

		ModelAndView result;
		
		ApplyFor apply = applyForService.findOne(id);
		if(apply.getStatus().equals("PENDING")){
			applyForService.delete(apply);
		}
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
