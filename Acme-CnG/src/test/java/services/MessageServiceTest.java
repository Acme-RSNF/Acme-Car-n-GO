
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private MessageService	messageService;


	// Tests --------------------------------------------------

	/* An actor who is authenticated must able to exchange messages with other actors. 
	 * 
	 * Para ello vamos a probar crear un mensaje estando registrado en el sistema y sin estar registrado en el sistema.*/
	@Test
	public void driverCreate() {
		Object testingData[][] = {
			{"customer1", null}, 
			{null, IllegalArgumentException.class}
		};

		for (int i = 1; i < testingData.length; i++) {
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void templateCreate(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			messageService.create(); // Intentamos crear un mensaje nuevo.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/* List the messages that he or she's got.
	 * 
	 * Este caso de uso se divide en 2 partes los mensajes enviados y los mensajes recividos por un usuario*/
	@Test
	public void driverListSent(){
		Object testingData[][] = {
			{"customer1",null},
			{null, IllegalArgumentException.class}
		};
		
		for (int i = 1; i < testingData.length; i++) {
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateListSent(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Message> messages = messageService.messagesSentByActorId(); // Intentamos crear un mensaje nuevo.
			Assert.isTrue(!messages.isEmpty());
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

}
