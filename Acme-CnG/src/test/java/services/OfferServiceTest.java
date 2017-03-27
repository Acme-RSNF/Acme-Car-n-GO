
package services;

import java.text.SimpleDateFormat;

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
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", "37.3914", "-5.9591", "Cordoba", "37.8881", "-4.7793", null}, 
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", null},
			{"admin", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			{null, "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			{"customer1", " ", "Descripcion", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			{"customer1", "Titulo", " ", "26/03/2017", "Sevilla", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			{"customer1", "Titulo", "Descripcion", "", "Sevilla", " ", " ", "Cordoba", " ", " ", IllegalArgumentException.class},
			{"customer1", "Titulo", "Descripcion", "26/03/2017", " ", " ", " ", "Cordoba", " ", " ", NullPointerException.class},
			{"customer1", "Titulo", "Descripcion", "26/03/2017", "Sevilla", " ", " ", " ", " ", " ", NullPointerException.class}
			

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

			OfferForm offerForm;
			offerForm = offerService.generate(); //Creamos una offer .

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

			Offer offer = offerService.reconstruct(offerForm, null);
			Assert.notNull(offer);
			offerService.save(offer); // Guardamos customer

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
}
