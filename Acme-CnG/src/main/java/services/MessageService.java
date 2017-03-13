package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import domain.Message;

import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class MessageService {
	
	// Managed repository -----------------------------------------------------
	/*
		@Autowired
		private MessageRepository	messageRepository;


		// Supporting services ----------------------------------------------------

		// Constructors -----------------------------------------------------------

		public MessageService() {
			super();
		}

		// Simple CRUD methods ----------------------------------------------------

		public Message create() {

			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			final Authority au = new Authority();
			au.setAuthority("CUSTOMER");
			Assert.isTrue(userAccount.getAuthorities().contains(au));

			Message result;
			result = new Message();

			Customer customer;
			customer = customerService.findByPrincipal();

			result.setSender(customer);

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

		public Collection<Message> findByPrincipal() {
			Collection<Message> result;
			UserAccount userAccount;

			userAccount = LoginService.getPrincipal();
			result = messageRepository.findByUserAccount(userAccount);

			return result;
		}

		public Message save(Message message) {

			Assert.notNull(message);
			Message result;
			result = messageRepository.save(message);

			return result;
		}

		public Message save2(Message message) {

			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			final Authority au = new Authority();
			au.setAuthority("CUSTOMER");
			Assert.isTrue(userAccount.getAuthorities().contains(au));

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
			Assert.isTrue(userAccount.getAuthorities().contains(au));

			Assert.notNull(message);
			Assert.isTrue(message.getId() != 0);

			messageRepository.delete(message);
		}

		// Other business methods -------------------------------------------------
	
*/
}
