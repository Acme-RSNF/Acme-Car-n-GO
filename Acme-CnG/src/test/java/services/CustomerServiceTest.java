
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import forms.CustomerForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------

	@Autowired
	private CustomerService	customerService;


	// Tests --------------------------------------------------

	/*
	 * An actor who is not authenticated must be able to:
	 * Watch a welcome page with a banner that publicises Acme Car'n go! and a button to
	 * register as a customer or to login.
	 * Para ello vamos a probar crear un applyFor estando registrado en el sistema como customer, sin
	 * estar registrado en el sistema y siendo admin.
	 * Comprobamos también que no permite hacer 2 deals desde el mismo customer hacia la misma deal
	 */
	@Test
	public void driverCreate() {
		Object testingData[][] = {
			{
				"username", "password", "password", "Nombre", "Apellidos", true, "correo@gmail.com", "+34 965456321", null
			}, {
				"username", "password", "password", "Nombre", "Apellidos", true, "correo@gmail.com", "965456321", null
			}, {
				"username", "password", "password", "Nombre", "Apellidos", false, "correo@gmail.com", "+34 965456321", IllegalArgumentException.class
			}, {
				"username", "password", "passwor", "Nombre", "Apellidos", true, "correo@gmail.com", "957645231", IllegalArgumentException.class
			}, {
				"username", "password", "password", "Nombre", "Apellidos", true, "correomail.com", "957654123", NullPointerException.class
			}, {
				"username", "password", "password", "Nombre", "Apellidos", true, "correo@mail.com", "4123", NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	protected void templateCreate(String username, String password, String password2, String name, String surname, Boolean agreed, String email, String phone, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			//authenticate(none); // Nos autenticamos como el usuario

			CustomerForm customerForm;
			customerForm = customerService.generateForm(); //Creamos una applyFor .
			customerForm.setUsername(username);
			customerForm.setPassword(password);
			customerForm.setPassword2(password2);
			customerForm.setAgreed(agreed);
			customerForm.setName(name);
			customerForm.setSurname(surname);
			customerForm.setEmail(email);
			customerForm.setPhone(phone);
			Customer customer = customerService.reconstruct(customerForm, null);
			Assert.isTrue(customer != null);
			customerService.save(customer); // Guardamos customer

			//unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	@Test
	public void driverEdit() {
		Object testingData[][] = {
			{
				"customer1", 50, null
			}, {
				"admin", 50, NullPointerException.class
			}, {
				null, 50, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			templateEdit((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateEdit(String user, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Customer customer = customerService.findOne(id);
			customer.setName("nombreNuevo");
			Customer customer2 = customerService.save(customer);//Guardamos el admin con el nombre modificado
			Assert.notNull(customer2);//Comprobamos que no sea nulo
			Assert.isTrue(customer2.getName().equals("nombreNuevo"));
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	//Tests---------------------------------------------------
	/*
	 * Eliminar un administrador
	 */
	@Test
	public void driverDelete() {
		Object testingData[][] = {
			{
				"customer1", 50, null
			}, {
				"admin", 50, NullPointerException.class
			}, {
				null, 50, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			templateEdit((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateDelete(String user, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Customer customer = customerService.findOne(id);
			customerService.delete(customer);//Eliminamos el administrador
			Assert.isNull(customer);//Comprobamos que no sea nulo
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

}
