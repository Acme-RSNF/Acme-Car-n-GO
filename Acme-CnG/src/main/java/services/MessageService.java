
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;


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

		result.setSender(actor);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(final int messageId) {
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
		Assert.isTrue(message.getId() != 0);

		messageRepository.delete(message);
	}

	// Other business methods -------------------------------------------------

}
