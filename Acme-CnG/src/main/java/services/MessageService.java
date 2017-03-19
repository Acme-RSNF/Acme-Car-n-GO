
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {

		Message result;
		result = new Message();

		Actor actor;
		actor = actorService.findByPrincipal();
		Date moment = new Date(System.currentTimeMillis() - 10);
		Collection<String> attachments = new ArrayList<String>();

		result.setSender(actor);
		result.setMoment(moment);
		result.setAttachment(attachments);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(int messageId) {
		Message result;

		result = messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Message save(Message message) {

		Assert.notNull(message);
		Message result;
		result = messageRepository.save(message);

		return result;
	}

	public void delete(Message message) {

		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.equals(message.getSender()) || actor.equals(message.getRecipient()));
		Assert.isTrue(message.getId() != 0);

		messageRepository.delete(message);
	}

	// Other business methods -------------------------------------------------

	public Collection<Message> messagesSentByActorId() {
		Collection<Message> result = new ArrayList<Message>();
		Actor actor = actorService.findByPrincipal();
		result = messageRepository.messagesSentByActorId(actor.getId());
		return result;
	}
	public Collection<Message> messagesReceivedByActorId() {
		Collection<Message> result = new ArrayList<Message>();
		Actor actor = actorService.findByPrincipal();
		result = actor.getReceived();
		return result;
	}

	//	public void sendMessage(Message message) {
	//
	//		// Creamos un segundo mensaje que será una copia del mensaje creado.
	//		Message message2 = create();
	//
	//		message2.setAttachment(message.getAttachment());
	//		message2.setMoment(message.getMoment());
	//		message2.setRecipient(message.getRecipient());
	//		message2.setSender(message.getSender());
	//		message2.setText(message.getText());
	//		message2.setTitle(message.getTitle());
	//
	//		// Guardamos los dos mensajes en la base de datos.
	//		save(message);
	//		save(message2);
	//
	//		// Añadimos los mensajes en la lista de mensajes enviados y recividos de los dos actores correspondients.
	//		Actor sender = message.getSender();
	//		Actor recipient = message.getRecipient();
	//
	//		Collection<Message> sent = sender.getSended();
	//		Collection<Message> recived = recipient.getReceived();
	//
	//		sent.add(message);
	//		recived.add(message2);
	//
	//		sender.setSended(sent);
	//		recipient.setReceived(recived);
	//
	//		actorService.save(sender);
	//		actorService.save(recipient);
	//	}

	// Forward ------------------------------------------------------------

	public MessageForm forward(int messageId) {
		MessageForm result = generate();
		Message message = findOne(messageId);

		result.setAttachment(message.getAttachment());
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setSender(actorService.findByPrincipal());

		return result;
	}

	// Form methods ----------------------------------------------------------

	public MessageForm generate() {
		MessageForm result = new MessageForm();

		result.setSender(actorService.findByPrincipal());

		return result;
	}

	public Message reconstruct(MessageForm messageForm, BindingResult binding) {

		Message result = create();

		Assert.isTrue(!messageForm.getSender().equals(messageForm.getRecipient()));

		result.setAttachment(messageForm.getAttachment());
		result.setRecipient(messageForm.getRecipient());
		result.setSender(actorService.findByPrincipal());
		result.setText(messageForm.getText());
		result.setTitle(messageForm.getTitle());

		validator.validate(result, binding);

		return result;

	}

}
