
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/customer/request")
public class CustomerRequestController extends AbstractController {

	//Services-------------------------

	@Autowired
	private RequestService	requestService;


	//Constructor----------------------

	public CustomerRequestController() {
		super();
	}

	//List--------------------------

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		requests = requestService.findByCreator();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/list.do");

		return result;
	}

	// Create -------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		RequestForm requestForm = requestService.generate();

		result = new ModelAndView("request/create");
		result.addObject("requestForm", requestForm);
		result.addObject("requestURI", "customer/request/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RequestForm requestForm, BindingResult binding) {

		ModelAndView result;
		Request request;

		if (binding.hasErrors()) {
			result = createEditModelAndView(requestForm);
		} else {
			try {
				request = requestService.reconstruct(requestForm, binding);
				requestService.save2(request);
				result = new ModelAndView("redirect:/request/list.do");
			} catch (Throwable oops) {
				String msgCode = "request.error";
				result = createEditModelAndView(requestForm, msgCode);
			}
		}

		return result;

	}

	// Ancillary methods ---------------------------------------

	protected ModelAndView createEditModelAndView(RequestForm requestForm) {
		ModelAndView result;

		result = createEditModelAndView(requestForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(RequestForm requestForm, String message) {
		ModelAndView result;

		result = new ModelAndView("request/create");
		result.addObject("requestForm", requestForm);
		result.addObject("message", message);

		return result;
	}

}
