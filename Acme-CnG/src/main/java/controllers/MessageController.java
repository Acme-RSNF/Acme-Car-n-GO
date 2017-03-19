
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

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Constructor -----------------------------------------

	public MessageController() {
		super();
	}

	// Listing ---------------------------------------------

	@RequestMapping(value = "/received", method = RequestMethod.GET)
	public ModelAndView listRecieved() {
		ModelAndView result;

		Collection<Message> messages = messageService.messagesReceivedByActorId();

		result = new ModelAndView("message/received");
		result.addObject("messages", messages);

		return result;
	}

	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	public ModelAndView listSent() {
		ModelAndView result;

		Collection<Message> messages = messageService.messagesSentByActorId();

		result = new ModelAndView("message/sent");
		result.addObject("messages", messages);

		return result;
	}

	// View ------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int messageId) {

		ModelAndView result;
		Message message = messageService.findOne(messageId);

		result = new ModelAndView("message/view");
		result.addObject("messageDisplay", message);

		return result;

	}

	// Edition ---------------------------------------------

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView write() {
		ModelAndView result;

		MessageForm messageForm = messageService.generate();
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/write");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@Valid MessageForm messageForm, BindingResult binding) {

		ModelAndView result;
		Message message;

		if (binding.hasErrors()) {
			result = createEditModelAndView(messageForm);
		} else {
			try {
				message = messageService.reconstruct(messageForm, binding);
				messageService.save(message);
				result = new ModelAndView("redirect:/message/sent.do");
			} catch (Throwable oops) {
				String msgCode = "message.error";
				result = createEditModelAndView(messageForm, msgCode);
			}
		}

		return result;

	}

	// Reply ---------------------------------------------------

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam int messageId) {

		ModelAndView result;
		MessageForm messageForm = messageService.generate();

		Actor actor = messageService.findOne(messageId).getSender();
		Collection<Actor> actors = new ArrayList<Actor>();

		actors.add(actor);

		result = new ModelAndView("message/forward");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);

		return result;

	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "save")
	public ModelAndView sendReply(@Valid MessageForm messageForm, BindingResult binding) {

		ModelAndView result;
		Message message;

		if (binding.hasErrors()) {
			result = createEditModelAndViewReply(messageForm);
		} else {
			try {
				message = messageService.reconstruct(messageForm, binding);
				messageService.save(message);
				result = new ModelAndView("redirect:/message/sent.do");
			} catch (Throwable oops) {
				String msgCode = "message.error";
				result = createEditModelAndViewReply(messageForm, msgCode);
			}
		}

		return result;

	}

	// Forward ---------------------------------------------------

	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam int messageId) {

		ModelAndView result;
		MessageForm messageForm = messageService.forward(messageId);

		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/forward");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);

		return result;

	}

	@RequestMapping(value = "/forward", method = RequestMethod.POST, params = "save")
	public ModelAndView sendForward(@Valid MessageForm messageForm, BindingResult binding) {

		ModelAndView result;
		Message message;

		if (binding.hasErrors()) {
			result = createEditModelAndViewForward(messageForm);
		} else {
			try {
				message = messageService.reconstruct(messageForm, binding);
				messageService.save(message);
				result = new ModelAndView("redirect:/message/sent.do");
			} catch (Throwable oops) {
				String msgCode = "message.error";
				result = createEditModelAndViewForward(messageForm, msgCode);
			}
		}

		return result;

	}

	// Delete ---------------------------------------------------

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam int messageId) {

		ModelAndView result;
		Message message = messageService.findOne(messageId);

		try {
			messageService.delete(message);
			result = new ModelAndView("redirect:/message/sent.do");
		} catch (Throwable oops) {
			String msgCode = "message.error.delete";
			result = createEditModelAndViewDelete(msgCode);
		}

		return result;

	}

	// Ancillary methods ---------------------------------------

	protected ModelAndView createEditModelAndView(MessageForm messageForm) {
		ModelAndView result;

		result = createEditModelAndView(messageForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(MessageForm messageForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/write");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);

		return result;

	}

	protected ModelAndView createEditModelAndViewReply(MessageForm messageForm) {
		ModelAndView result;

		result = createEditModelAndView(messageForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndViewReply(MessageForm messageForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/reply");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);

		return result;

	}

	protected ModelAndView createEditModelAndViewForward(MessageForm messageForm) {
		ModelAndView result;

		result = createEditModelAndView(messageForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndViewForward(MessageForm messageForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/forward");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);

		return result;

	}

	protected ModelAndView createEditModelAndViewDelete() {
		ModelAndView result;

		result = createEditModelAndView(null);

		return result;

	}

	protected ModelAndView createEditModelAndViewDelete(String message) {
		ModelAndView result;

		result = new ModelAndView("message/sent");

		return result;

	}
}
