
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Coordinate;
import domain.Request;
import forms.RequestForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private RequestService	requestService;


	// Tests --------------------------------------------------

	/*
	 * Post a request in which he or she informs that he or she wishes to move from a
	 * place to another one and would like to find someone with whom he or she can share
	 * the trip
	 * 
	 * Para ello vamos a probar crear un request estando registrado en el sistema
	 * como customer y como usuario no autenticado
	 */

	@Test
	public void driverCreate() {
		Object testingData[][] = {
			{
				"customer1", "Titulo", "Descripcion", "05/05/2017", "Algodonales", "36.89609", "-5.40115", "Sevilla", "36.89444", "-5.98196", null
			}, {
				null, "Titulo", "Descripcion", "05/05/2017", "Algodonales", "36.89609", "-5.40115", "Sevilla", "36.89444", "-5.98196", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void templateCreate(String user, String title, String description, String moment, String origin, String Xorigin, String Yorigin, String destination, String Xdestination, String Ydestination, Class<?> expected) {
		Class<?> caught;

		Coordinate originCoordinate = new Coordinate();
		Coordinate destinationCoordinate = new Coordinate();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");

		caught = null;
		try {
			authenticate(user); // Nos autenticamos como el usuario

			RequestForm requestForm;
			requestForm = requestService.generate(); //Creamos un request.

			requestForm.setTitle(title);

			requestForm.setMoment(fecha.parse(moment));
			requestForm.setDescription(description);

			requestForm.setOrigin(origin);
			originCoordinate.setLatitude(Xorigin);
			originCoordinate.setLatitude(Yorigin);
			requestForm.setOriginCoordinate(originCoordinate);

			requestForm.setDestination(destination);
			destinationCoordinate.setLatitude(Xdestination);
			destinationCoordinate.setLatitude(Ydestination);
			requestForm.setDestinationCoordinate(destinationCoordinate);

			Request request = requestService.reconstruct(requestForm, null);
			Assert.notNull(request);
			requestService.save(request); // Guardamos request

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * List the requests.
	 * 
	 * Vamos a registrarnos como un customer y listar las requests
	 * y vamos a hacer lo mismo como usuario no autenticado
	 */
	@Test
	public void driverList() {
		Object testingData[][] = {
			{
				"customer1", null
			}, // Obtenedremos todas las requests al ser un customer.
			{
				null, IllegalArgumentException.class
			}
		// Y tambien lo haremos como usuario no autenticado.
		};

		for (int i = 0; i < testingData.length; i++)
			templateList((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateList(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Request> requests = requestService.findAll(); // Obtenemos los requests.
			Assert.isTrue(!requests.isEmpty()); // Comprobamos que la lista de requests no esta vacía
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Delete a request.
	 * 
	 * Vamos a registrarnos como un customer y borrar una request
	 * y vamos a hacer lo mismo como un ususario no autenticado
	 */
	@Test
	public void driverDelete() {
		Object testingData[][] = {
			{
				"customer1", 64, null
			}, // Borraremos una request al ser un customer.
			{
				null, 64, IllegalArgumentException.class
			}
		// pero no podemos borrarla como usuario no autenticado.
		};

		for (int i = 0; i < testingData.length; i++)
			templateDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Request request = requestService.findOne(id); //Buscamos la request a borrar
			requestService.delete(request); // Borramos la request.
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Ban or unban a request.
	 * 
	 * Vamos a registrarnos como un admin y banear una request
	 * y vamos a hacer lo mismo como un customer, pero éste no podría
	 */
	@Test
	public void driverBanUnban() {
		Object testingData[][] = {
			{
				"admin", 64, null
			}, // Banearemos una request(no está baneada) al ser un admin.
			{
				"customer1", 64, IllegalArgumentException.class
			}
		// Y probaremos a hacerlo como customer, pero no se podría.
		};

		for (int i = 0; i < testingData.length; i++)
			templateBanUnban((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateBanUnban(String username, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			requestService.banUnbanRequest(requestService.findOne(id)); // Obtenemos los requests.
			Assert.isTrue(requestService.findOne(id).getBanned() == true); // Comprobamos que la request esta baneada
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

}
