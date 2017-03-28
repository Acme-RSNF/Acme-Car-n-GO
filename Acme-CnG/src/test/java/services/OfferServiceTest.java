
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
import domain.Offer;
import forms.OfferForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class OfferServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private OfferService	offerService;


	// Tests --------------------------------------------------

	/*
	 * An actor who is authenticated as a customer must be able to:
	 * o Post an offer in which he or she advertises that he's going to move from a place to
	 * another place and would like to share his or her car with someone else.
	 * 
	 * Para ello vamos a probar crear un offer estando registrado en el sistema como customer, sin
	 * estar registrado en el sistema y siendo admin.
	 */

	@Test
	public void driverCreate() {
		Object testingData[][] = {
			// Crea una offer correctamente con todos los valores	
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", "37.3914", "-5.9591", "Cordoba", "37.8881", "-4.7793", null}, 
			// Crea una offer correctamente sin las coordenadas destino
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", "37.3914", "-5.9591", "Cordoba", " ", " ", null}, 
			// Crea una offer correctamente sin las coordenadas origen
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", "37.8881", "-4.7793", null}, 
			// Crea una offer correctamente sin las coordenadas
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", null},
			// Intenta crear una offer siendo admin
			{"admin", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			// Intenta crear una offer sin estar autenticado
			{null, "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			// Intenta crear una offer sin el titulo
			{"customer1", " ", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			// Intenta crear una offer sin la descripción
			{"customer1", "Titulo", " ", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			// Intenta crear una offer sin la fecha
			{"customer1", "Titulo", "Descripcion", "", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			// Intenta crear una offer sin el origen
			{"customer1", "Titulo", "Descripcion", "26/03/2017", " ", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			// Intenta crear una offer sin el destino
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", " ", " ", " ", NullPointerException.class}
			

		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	protected void templateCreate(String user, String title, String description, String moment, String origin, String Xorigin, String Yorigin, String destination, String Xdestination, String Ydestination, Class<?> expected) {
		Class<?> caught;
		// Creamos el objeto originCoordinate de tipo Coodinate 
		Coordinate originCoordinate = new Coordinate(); 
		 // Creamos el objeto destinationCoordinate de tipo Coodinate 
		Coordinate destinationCoordinate = new Coordinate();
		// Creamos el SimpleDateFormat para transformar el String que le pasamos al metodo a Date
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");

		caught = null;
		try {
			authenticate(user); // Nos autenticamos como el usuario

			OfferForm offerForm;				 // Creamos el objeto offerForm
			offerForm = offerService.generate(); //Creamos una offerForm .

			// Añadimos los datos a la offerForm
			offerForm.setTitle(title);
			Assert.isTrue(!moment.equals(""));
			offerForm.setMoment(fecha.parse(moment));
			offerForm.setDescription(description);

			offerForm.setOrigin(origin);
			originCoordinate.setLatitude(Xorigin);
			originCoordinate.setLatitude(Yorigin);
			offerForm.setOriginCoordinate(originCoordinate);

			offerForm.setDestination(destination);
			destinationCoordinate.setLatitude(Xdestination);
			destinationCoordinate.setLatitude(Ydestination);
			offerForm.setDestinationCoordinate(destinationCoordinate);
			
			// Reconstruimos y validamos el offerForm
			Offer offer = offerService.reconstruct(offerForm, null);
			Assert.notNull(offer); // Comprobamos que el objeto que nos devuelve el validate no es nulo
			offerService.save(offer); // Guardamos customer

			unauthenticate(); // Nos desautenticamos
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/*
	 * ELIMINAR
	 */
	
	
	@Test
	public void driverDelete() {
		Object testingData[][] = {
			{"customer3", 61, null}, // Elimina una offer suya, la cual no tiene ninguna ApplyFor
			{"customer1", 61, IllegalArgumentException.class}, // Intenta eliminar un offer que no es suya
			{"admin", 61, IllegalArgumentException.class}, // Intenta eliminar un offer siendo admin
			{"null", 61, IllegalArgumentException.class}, // Intenta eliminar sin autenticarse
			{"customer1", 59, IllegalArgumentException.class}, // Intenta eliminar una offer con applies
			{"customer1", 0, IllegalArgumentException.class} // Intenta eliminar una offer que no existe
				

		};

		for (int i = 0; i < testingData.length; i++)
			templateDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(String user, int offerId, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user); // Nos autenticamos como el usuario
			Offer offer = offerService.findOne(offerId); // Buscamos el offer por la id que recibimos
			Assert.notNull(offer); // Comprobamos que el offer que hemos buscado no sea nulo
			offerService.delete(offer); // Eliminamos la offer
			unauthenticate(); // Nos desautenticamos
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/*
	 * List the offer.
	 * 
	 * Vamos a registrarnos como un customer y listar las offer
	 * y vamos a hacer lo mismo como usuario no autenticado
	 */
	@Test
	public void driverList() {
		Object testingData[][] = {
			{"customer1", null}, // Obtenedremos todas las offer al ser un customer.
			{"admin", null}, // Obtendremos todas las offers
			{null, IllegalArgumentException.class} // Y tambien lo haremos como usuario no autenticado.
		};

		for (int i = 0; i < testingData.length; i++)
			templateList((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateList(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Offer> offers = offerService.findAll(); // Obtenemos los offer.
			Assert.isTrue(!offers.isEmpty()); // Comprobamos que la lista de requests no esta vacía
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/*
	 * Ban or unban a offer.
	 * 
	 * Vamos a registrarnos como un admin y banear una offer
	 * y vamos a hacer lo mismo como un customer, pero éste no podría
	 */
	@Test
	public void driverBanUnban() {
		Object testingData[][] = {
			// Desbanearemos una request(está baneada) al ser un admin.
			{"admin", 59, null}, 
			// Banearemos una request(no está baneada) al ser un admin.
			{"admin", 60, null}, 
			 // Y probaremos a hacerlo como customer, pero no se podría.
			{"customer1", 59, IllegalArgumentException.class},
			 // Y probaremos a hacerlo sin autenticar, pero no se podría.
			{null, 59, IllegalArgumentException.class},
			// Intentamos banear/desbanear una offer que no existe 
			{"admin", 79, IllegalArgumentException.class}
		};

		for (int i = 0; i < testingData.length; i++)
			templateBanUnban((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateBanUnban(String username, int id, Class<?> expected) {
		Class<?> caught;

		Boolean ban;
		Offer offer;
		
		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			
			offer = offerService.findOne(id);
			ban = offer.getBanned();
			offerService.banUnbanOffer(offer); // Obtenemos los requests.
			offer = offerService.save(offer);
			Assert.isTrue(offer.getBanned() != ban); // Comprobamos que la request esta baneada
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*
	 * Search for offers using a single keyword that must appear somewhere
	 * in their titles, descriptions, or places.
	 * 
	 * 
	 * Vamos a registrarnos como un customer y buscar offers con una palabra clave
	 * y vamos a hacer lo mismo como usuario no autenticado
	 */
	@Test
	public void driverSearch() {
		Object testingData[][] = {
			// Obtenedremos los resultados de la busqueda por títulor.
			{"customer1", "Offer 2", null}, 
			// Obtenedremos los resultados de la busqueda por descripción.
			{"customer1", "descripcion", null}, 
			// Obtenedremos los resultados de la busqueda por origen.
			{"customer1", "Madrid", null}, 
			// Obtenedremos los resultados de la busqueda por destino.
			{"customer1", "Sevilla", null}, 
			// Intentamos hacer una búsqueda siendo la key nula
			{"customer1", "", IllegalArgumentException.class},
			// Intentamos hacer una búsqueda siendo admin
			{"admin", "Offer 2", IllegalArgumentException.class},
			// Intentamos hacer una búsqueda sin estar autenticados
			{null, "Offer 2", IllegalArgumentException.class}
		};

		for (int i = 0; i < testingData.length; i++)
			templateSearch((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateSearch(String username, String keyword, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			Collection<Offer> offers = offerService.findByKey(keyword); // Obtenemos los requests de la busqueda.
			Assert.isTrue(!offers.isEmpty()); // Comprobamos que la lista de requests no esta vacía
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
}
