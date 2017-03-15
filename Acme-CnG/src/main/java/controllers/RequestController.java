
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import domain.Request;

@Controller
@RequestMapping("/request")
public class RequestController extends AbstractController {

	//Services-------------------------

	@Autowired
	private RequestService	requestService;


	//Constructor----------------------

	public RequestController() {
		super();
	}

	//List--------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		requests = requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/list.do");

		return result;
	}

}
