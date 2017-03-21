
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CommentService;
import services.CommentableService;
import services.CustomerService;
import services.OfferService;
import domain.Comment;
import domain.Commentable;
import forms.CommentForm;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	//Services-------------------------

	@Autowired
	private CommentService			commentService;

	@Autowired
	private CommentableService		commentableService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private OfferService			offerService;


	//Constructor----------------------

	public CommentController() {
		super();
	}

	//List--------------------------

	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public ModelAndView listAdmin() {
		ModelAndView result;
		Collection<Commentable> aux;
		Collection<Comment> comments = new ArrayList<Comment>();
		aux = commentableService.findAll();
		for (Commentable c : aux)
			comments.addAll(c.getComments());

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/listAdmin.do");

		return result;
	}

	@RequestMapping(value = "/banUnbanComment", method = RequestMethod.GET)
	public ModelAndView banUnbanComment(@RequestParam int commentId) {
		ModelAndView result;
		Comment c = commentService.findOne(commentId);
		try {
			commentService.banUnbanComment(c);
			result = listAdmin();
		} catch (Throwable oops) {
			result = listAdmin();
			result.addObject("message", "master-page.commit.error");
		}

		return result;
	}

	//Creation-------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int commentableId) {

		ModelAndView result;
		CommentForm commentForm;

		commentForm = commentService.generateForm(commentableId);

		result = createEditModelAndView(commentForm, null);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CommentForm commentForm, BindingResult binding) {

		ModelAndView result = new ModelAndView();
		Comment comment;

		if (binding.hasErrors())
			result = createEditModelAndView(commentForm, null);
		else
			try {
				comment = commentService.reconstruct(commentForm, binding);
				comment = commentService.save(comment);
				int id = comment.getCommentable().getId();
				if (customerService.findOne(id) != null) {
					result = new ModelAndView("redirect:../customer/displayById.do?customerId=" + id);
					String requestURI = "customer/displayById.do?customerId=" + id;
					result.addObject("requestURI", requestURI);
				} else if (offerService.findOne(id) != null) {
					result = new ModelAndView("redirect:../offer/display.do?offerId=" + id);
					String requestURI = "offer/display.do?offerId=" + id;
					result.addObject("requestURI", requestURI);
				} else if (administratorService.findOne(id) != null) {
					result = new ModelAndView("redirect:../administrator/displayById.do?administratorId=" + id);
					String requestURI = "administrator/displayById.do?administratorId=" + id;
					result.addObject("requestURI", requestURI);
				} else {
					result = new ModelAndView("redirect:../request/display.do?requestId=" + id);
					String requestURI = "request/display.do?requestId=" + id;
					result.addObject("requestURI", requestURI);
				}
			} catch (Throwable oops) {
				String msgCode;
				if (oops.getMessage().equals("notCommentator")) {
					msgCode = "comment.notCommentator";
					result = createEditModelAndView(commentForm, msgCode);
				}
				if (oops.getMessage().equals("notCommentable")) {
					msgCode = "comment.notCommentable";
					result = createEditModelAndView(commentForm, msgCode);
				}
			}
		return result;
	}

	//Ancillary Methods---------------------------

	protected ModelAndView createEditModelAndView(CommentForm commentForm, String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("commentForm", commentForm);
		result.addObject("message", message);

		return result;

	}

}
