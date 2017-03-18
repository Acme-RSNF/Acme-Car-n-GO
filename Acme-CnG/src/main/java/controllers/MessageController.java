
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services --------------------------------------------

	@Autowired
	private MessageService	messageService;


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

	// Edition ---------------------------------------------

}
