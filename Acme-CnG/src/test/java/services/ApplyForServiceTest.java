
package services;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import utilities.AbstractTest;

import domain.ApplyFor;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ApplyForServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private ApplyForService	applyForService;

	@Autowired
	private DealService	dealService;



	// Tests --------------------------------------------------

	/*An actor who is authenticated as a customer must be able to Apply for an offer or a request, 
	 * which must be accepted by the customer who posted it. Applications can be pending, accepted, or denied. 
	 * 
	 * Para ello vamos a probar crear un applyFor estando registrado en el sistema como customer, sin 
	 * estar registrado en el sistema y siendo admin.
	 * Comprobamos también que no permite hacer 2 deals desde el mismo customer hacia la misma deal*/
	@Test
	public void driverApplyFor() {
		Object testingData[][] = {
			{"customer1", 61, null}, 
			{"customer2", 60, null},
			{"customer2", 61, null},
			{null, 61, IllegalArgumentException.class},
			{"admin", 61, IllegalArgumentException.class},
			{"customer1", 65, IllegalArgumentException.class}
		};

		for (int i = 1; i < testingData.length; i++) {
			templateCreate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateCreate(String username, int dealId, Class<?> expected) {
		Class<?> caught;
		ApplyFor applyFor;
		
		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			applyFor = applyForService.create(); //Creamos una applyFor .
			applyFor.setDeal(dealService.findOne(dealId)); // Le asignamos una deal en la que no esta registrado
			applyForService.save(applyFor); // Guardamos la applyFor
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/*An actor who is authenticated as a customer must be able to Apply for an offer or a request, 
	 * which must be accepted by the customer who posted it. Applications can be pending, accepted, or denied. 
	 * 
	 * Para ello vamos a probar crear un applyFor estando registrado en el sistema como customer, sin 
	 * estar registrado en el sistema y siendo admin.
	 * Comprobamos también que no permite hacer 2 deals desde el mismo customer hacia la misma deal*/
	@Test
	public void driverDelete() {
		Object testingData[][] = {
			{"customer1", 72, null}, 
			{"customer2", 68, null},
			{null, 73, IllegalArgumentException.class},
			{"admin", 73, IllegalArgumentException.class},
			{"customer1", 73, IllegalArgumentException.class},
			{"customer3", 70, IllegalArgumentException.class}
		};

		for (int i = 1; i < testingData.length; i++) {
			templateDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void templateDelete(String username, int applyForId, Class<?> expected) {
		Class<?> caught;
		ApplyFor applyFor;
		
		caught = null;
		try {
			authenticate(username); // Nos autenticamos como el usuario
			applyFor = applyForService.findOne(applyForId); //Creamos una applyFor .
			applyForService.delete(applyFor); // Eliminamos la applyFor
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	
}
