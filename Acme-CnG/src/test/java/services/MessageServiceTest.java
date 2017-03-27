
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
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private MessageService	messageService;


	// Tests --------------------------------------------------

	/*
	 * An actor who is authenticated must able to exchange messages with other actors.
	 * 
	 * Para ello vamos a probar crear un mensaje estando registrado en el sistema y sin estar registrado en el sistema.
	 */
	@Test
	public void driverCreate() {
		Object testingData[][] = {
			{
				"customer1", null
			}, {
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
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

	/*
	 * List the messages that he or she's got.
	 * 
	 * Este caso de uso se divide en 2 partes los mensajes enviados y los mensajes recividos por un usuario
	 */
	@Test
	public void driverListSent() {
		Object testingData[][] = {
			{
				"customer1", null
			}, // En este primer caso obtenemos los mensajes enviados por el customer 1.
			{
				null, IllegalArgumentException.class
			}
		// Probamos ahora a solicitar los mensajes de un usuario no registrado y no debería permitirnoslo.
		};

		for (int i = 0; i < testingData.length; i++)
			templateListSent((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListSent(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Message> messages = messageService.messagesSentByActorId(); // Obtenemos los mensajes enviados por el usuario conectado.
			Assert.isTrue(!messages.isEmpty()); // Comprobamos que la lista de mensajes no esta vacía
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * List the messages that he or she's got.
	 * 
	 * Este caso de uso se divide en 2 partes los mensajes enviados y los mensajes recividos por un usuario
	 */
	@Test
	public void driverListRecieved() {
		Object testingData[][] = {
			{
				"customer1", null
			}, // En este primer caso obtenemos los mensajes recibidos por el customer 1.
			{
				null, IllegalArgumentException.class
			}
		// Probamos ahora a solicitar los mensajes de un usuario no registrado y no debería permitirnoslo.
		};

		for (int i = 0; i < testingData.length; i++)
			templateListRecieved((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateListRecieved(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Message> messages = messageService.messagesReceivedByActorId(); // Obtenemos los mensajes recibidos por el usuario conectado.
			Assert.isTrue(!messages.isEmpty()); // Comprobamos que la lista de mensajes no esta vacía
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Forward a message that he or she's got.
	 * Un actor sólo puede reenviar un mensaje si lo ha enviado o recibido.
	 */
	@Test
	public void driverForward() {
		Object testingData[][] = {
			{
				"customer1", 55, null
			}, // Reenviamos el mensaje con id 55 del cual customer 1 es sender o recipient.
			{
				"customer3", 55, IllegalArgumentException.class
			}
		//Probamos a reenviar un el mensaje con id 55 que no ha sido enviado o recibido por el customer 3.
		};

		for (int i = 0; i < testingData.length; i++)
			templateForward((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateForward(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			MessageForm messageForm = messageService.forward(id); // Reenviamos el mensaje dado.
			Assert.isTrue(!messageForm.getText().equals(null)); // Comprobamos que los campos del mensaje no son nulos.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Reply a message that he or she's got.
	 * Sólo se puede responder a los mensajes que se han recivido
	 */
	@Test
	public void driverReply() {
		Object testingData[][] = {
			{
				"customer2", 55, null
			}, // Respondemos el mensaje con id 55 del cual customer 2 es recipient.
			{
				"customer3", 55, IllegalArgumentException.class
			}
		//Probamos a responder un el mensaje con id 55 que no ha sido enviado o recibido por el customer 3.
		};

		for (int i = 0; i < testingData.length; i++)
			templateReply((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateReply(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Actor> actors = messageService.reply(id); // Reenviamos el mensaje dado.
			Assert.isTrue(!actors.isEmpty() && actors.size() == 1); // Comprobamos que la lista de actores no es vacía y tiene longitud 1.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Erase a message that he or she's got.
	 * Un actor sólo puede eliminar un mensaje si es el sender o el recipient.
	 */
	@Test
	public void driverErase() {
		Object testingData[][] = {
			{
				"customer2", 55, null
			}, // Eliminamos el mensaje con id 55 del cual customer 2 es recipient.
			{
				"customer3", 55, IllegalArgumentException.class
			}
		//Probamos a eliminar un el mensaje con id 55 que no ha sido enviado o recibido por el customer 3.
		};

		for (int i = 0; i < testingData.length; i++)
			templateErase((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateErase(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Message message = messageService.findOne(id);
			messageService.delete(message); // eliminamos el mensaje dado.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

}
