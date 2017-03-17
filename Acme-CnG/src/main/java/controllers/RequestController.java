
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		Collection<Request> requests = new ArrayList<Request>();
		Collection<Request> aux;
		aux = requestService.findAll();

		for (Request r : aux)
			if (r.getBanned() == false)
				requests.add(r);

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/list.do");

		return result;
	}

	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public ModelAndView listAdmin() {
		ModelAndView result;

		Collection<Request> requests;
		requests = requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/list.do");

		return result;
	}

	@RequestMapping(value = "/banUnbanRequest", method = RequestMethod.GET)
	public ModelAndView banUnbanRequest(@RequestParam int requestId) {
		ModelAndView result;
		Request r = requestService.findOne(requestId);
		try {
			requestService.banUnbanRequest(r);
			result = listAdmin();
		} catch (Throwable oops) {
			result = listAdmin();
			result.addObject("message", "master-page.commit.error");
		}

		return result;
	}

}
