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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	//Services-------------------------

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Creation ------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CustomerForm customerForm;

		customerForm = customerService.generateForm();
		result = createEditModelAndView(customerForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CustomerForm customerForm, BindingResult binding) {
		ModelAndView result;
		Customer customer;

		if (binding.hasErrors())
			result = createEditModelAndView(customerForm);
		else
			try {
				customer = customerService.reconstruct(customerForm, binding);
				customerService.save(customer);
				result = new ModelAndView("redirect:../security/login.do");
			} catch (Throwable oops) {
				String msgCode = "customer.register.error";
				if (oops.getMessage().equals("notEqualPassword"))
					msgCode = "customer.register.notEqualPassword";
				else if (oops.getMessage().equals("agreedNotAccepted"))
					msgCode = "customer.register.agreedNotAccepted";
				result = createEditModelAndView(customerForm, msgCode);
			}

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
