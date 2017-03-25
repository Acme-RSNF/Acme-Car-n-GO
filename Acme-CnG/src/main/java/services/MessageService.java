
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
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

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

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

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Collection<Message> result;

		result = messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(int messageId) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Message result;

		result = messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Message save(Message message) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Assert.notNull(message);
		Message result;
		result = messageRepository.save(message);

		return result;
	}

	public void delete(Message message) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));
		Assert.isTrue(message.getRecipient().equals(actorService.findByPrincipal()) || message.getSender().equals(actorService.findByPrincipal()));

		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.equals(message.getSender()) || actor.equals(message.getRecipient()));
		Assert.isTrue(message.getId() != 0);

		messageRepository.delete(message);
	}

	// Other business methods -------------------------------------------------

	public Collection<Message> messagesSentByActorId() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Collection<Message> result = new ArrayList<Message>();
		Actor actor = actorService.findByPrincipal();
		result = messageRepository.messagesSentByActorId(actor.getId());
		return result;
	}
	
	public Collection<Message> messagesReceivedByActorId() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Collection<Message> result = new ArrayList<Message>();
		Actor actor = actorService.findByPrincipal();
		result = actor.getReceived();
		return result;
	}
	
	// Reply --------------------------------------------------------------
	
	public Collection<Actor> reply(int messageId){
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));
		
		Message message = findOne(messageId);
		Actor actor = actorService.findByPrincipal();
		
		Assert.isTrue(message.getRecipient().equals(actor));
		
		Actor sender = message.getSender();
		Collection<Actor> actors = new ArrayList<Actor>();

		actors.add(sender);
		
		return actors;
	}

	// Forward ------------------------------------------------------------

	public MessageForm forward(int messageId) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		MessageForm result = generate();
		Message message = findOne(messageId);
		
		Assert.isTrue(message.getRecipient().equals(actorService.findByPrincipal()) || message.getSender().equals(actorService.findByPrincipal()));

		result.setAttachment(message.getAttachment());
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setSender(actorService.findByPrincipal());

		return result;
	}

	// Form methods ----------------------------------------------------------

	public MessageForm generate() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		MessageForm result = new MessageForm();

		result.setSender(actorService.findByPrincipal());

		return result;
	}

	public Message reconstruct(MessageForm messageForm, BindingResult binding) {
		UrlValidator url = new UrlValidator();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("CUSTOMER");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));

		Message result = create();

		Assert.isTrue(!messageForm.getSender().equals(messageForm.getRecipient()));
		if( !messageForm.getAttachment().isEmpty()){
			for(String s : messageForm.getAttachment()){
				Assert.isTrue(url.isValid(s),"badAttachment");
			}
		}
		result.setAttachment(messageForm.getAttachment());
		result.setRecipient(messageForm.getRecipient());
		result.setSender(actorService.findByPrincipal());
		result.setText(messageForm.getText());
		result.setTitle(messageForm.getTitle());

		validator.validate(result, binding);

		return result;

	}

}
