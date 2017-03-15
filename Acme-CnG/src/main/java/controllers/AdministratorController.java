/*
 * AdministratorController.java
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
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentableService;
import services.CustomerService;
import services.DealService;
import domain.Actor;
import domain.Customer;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private DealService			dealService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard -----------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		ModelAndView result;

		//C
		Double ratioOVSR = dealService.ratioOfferVsRequest();
		Double avgNORC = customerService.avgOfferRequestCustomer();
		Double avgNAOR = dealService.avgApplyDeal();
		Collection<Customer> customerMAA = customerService.customerApplyAccepted();
		Collection<Customer> customerMAD = customerService.customerApplyDenied();
		//B
		Double avgCAOR = actorService.avgCommentByActor();
		Double avgPAC = commentableService.avgComment();
		Collection<Actor> actorPavgC = actorService.actorAvgCommentPlusTenPercent();
		Collection<Actor> actorMavgC = actorService.actorAvgCommentMinusTenPercent();
		//A
		Collection<Double> minavgmaxMSA = actorService.minAvgMaxSent();
		Collection<Double> minavgmaxMRA = actorService.minAvgMaxReceived();
		Collection<Actor> actorSM = actorService.actorSentMoreMessage();
		Collection<Actor> actorRM = actorService.actorReceivedMoreMessage();

		result = new ModelAndView("administrator/dashboard");

		//C
		result.addObject("ratioOVSR", ratioOVSR);
		result.addObject("avgNORC", avgNORC);
		result.addObject("avgNAOR", avgNAOR);
		result.addObject("customerMAA", customerMAA);
		result.addObject("customerMAD", customerMAD);
		//B
		result.addObject("avgCAOR", avgCAOR);
		result.addObject("avgPAC", avgPAC);
		result.addObject("actorPavgC", actorPavgC);
		result.addObject("actorMavgC", actorMavgC);
		//A
		result.addObject("minavgmaxMSA", minavgmaxMSA);
		result.addObject("minavgmaxMRA", minavgmaxMRA);
		result.addObject("actorSM", actorSM);
		result.addObject("actorRM", actorRM);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;

	}

}
