
package controllers;

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
}
