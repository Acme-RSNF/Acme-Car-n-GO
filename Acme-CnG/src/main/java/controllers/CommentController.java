
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	//Services-------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;


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

}
