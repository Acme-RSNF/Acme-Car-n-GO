
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import utilities.AbstractTest;
import domain.Comment;
import forms.CommentForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// The SUT --------------------------------------------------

	@Autowired
	private CommentService	commentService;


	// Tests ----------------------------------------------------

	/*
	 * An admin can ban or unban a comment that he or she finds inappropriate.
	 */
	@Test
	public void driveBanUnbanComment() {
		Object testingData[][] = {
			{
				"admin", 77, null
			}, // Marcamos como no apropiado un comentario.
			{
				"customer3", 77, IllegalArgumentException.class
			}, // Un cliente intenta marcar como inapropiado un comentario.
			{
				"admin", 78, null
			}, // Marcamos como apropiado un comentario que estaba marcado como inapropiado.
			{
				"customer3", 78, IllegalArgumentException.class
			}
		// Un cliente intenta marcar como apropiado un comentario que estaba marcado como inapropiado.
		};

		for (int i = 0; i < testingData.length; i++)
			templateBanUnbanComment((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateBanUnbanComment(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Comment comment = commentService.findOne(id); // Obtenemos el comentario al cual queremos hacer ban o unban.
			commentService.banUnbanComment(comment); // Realizamos la operación de ban o unban dependiendo del mensaje.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * An actor who is authenticated can post a comment on another actor, on an offer, or a request.
	 */
	@Test
	public void drivePostComment() {
		Object testingData[][] = {
			{
				"admin", 52, null
			}, // Un administrador escribe un comentario en el perfil de otro actor del sistema.
			{
				"customer2", 62, null
			}, // Un cliente escribe un comentario en una offer.
			{
				"customer1", 65, null
			}, // Un cliente escribe un comentario en una request.
			{
				null, 64, IllegalArgumentException.class
			}
		// Un usuario no registrado intenta escribir un comentario.
		};

		for (int i = 0; i < testingData.length; i++)
			templatePostComment((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templatePostComment(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			CommentForm commentForm = commentService.generateForm(id); // Generamos el formulario con la información que se va a escribir en el comentario.
			// Rellenamos el formulario. 
			commentForm.setStars(4);
			commentForm.setText("No me gusta");
			commentForm.setTitle("Lo siento, pero no");
			BindingResult binding = null;
			commentService.reconstruct(commentForm, binding); // Realizamos la operación de ban o unban dependiendo del mensaje.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

}
