
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
import domain.Administrator;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// The SUT -----------------------------------------------
	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private DealService	dealService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	private ActorService actorService;
	
	@Autowired 
	private CommentableService commentableService;


	//Tests---------------------------------------------------
	/*
	 * Creacion de un administrador
	 */
	@Test
	public void driverCreate() {
		Object testingData[][] = {
			{
				"admin", "username", "password", "password", "Nombre", "Apellidos", "correo@gmail.com", "+34 965456321", null
			}, {
				"customer1", "username", "password", "password", "Nombre", "Apellidos", "correo@gmail.com", "+34 965456321", IllegalArgumentException.class
			}, {
				null, "username", "password", "password", "Nombre", "Apellidos", "correo@gmail.com", "+34 965456321", IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			templateCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	protected void templateCreate(String user, String username, String password, String password2, String name, String surname, String email, String phone, Class<?> expected) {
		Class<?> caught;
		Administrator admin;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			admin = administratorService.create();//Creamos un administrador y le pasamos los parametros
			admin.getUserAccount().setUsername(username);
			admin.getUserAccount().setPassword(password);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setEmail(email);
			admin.setPhone(phone);
			Assert.notNull(admin);//Comprobamos que no sea nulo
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	//Tests---------------------------------------------------
	/*
	 * Edicion de un administrador
	 */
	@Test
	public void driverEdit() {
		Object testingData[][] = {
			{
				"admin", 49, null
			}, {
				"customer1", 49, IllegalArgumentException.class
			}, {
				null, 49, IllegalArgumentException.class
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
			Administrator admin = administratorService.findOne(id);
			admin.setName("nombreNuevo");
			Administrator admin2 = administratorService.save(admin);//Guardamos el admin con el nombre modificado
			Assert.notNull(admin2);//Comprobamos que no sea nulo
			Assert.isTrue(admin2.getName().equals("nombreNuevo"));
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
				{"admin", 49,IllegalArgumentException.class},
				{"customer1", 49,IllegalArgumentException.class},
				{null, 49,IllegalArgumentException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			templateDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void templateDelete(String user, int id, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Administrator admin = administratorService.findOne(id);
			Assert.notNull(admin);//Comprobamos que no sea nulo
			administratorService.delete(admin);//Eliminamos el administrador
			Administrator admin2 = administratorService.findOne(id);//Buscamos el admin para comprobar que se haya eliminado
			Assert.isNull(admin2);
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	/*DashBoard C
	 * Display a dashboard with the following information:
		- Ratio of offers versus requests.
		- Average number of offers and request per customer.
	
		- Average number of applications per offer or request.
		- The customer who has more applications accepted.
		- The customer who has more applications denied.

	 */
	@Test
	public void driverDashBoardC() {
		Object testingData[][] = {
			{"admin",null}, 
			{"customer1",IllegalArgumentException.class},
			{null,IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			templateDashBoardC((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	protected void templateDashBoardC(String user,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Double d=dealService.ratioOfferVsRequest();//Obtenemos el ratio de las offer vs request
			Assert.notNull(d);//Comprobamos que no sea nulo
			Double e=customerService.avgOfferRequestCustomer();//Obtenemos la media de offer y request por customer
			Assert.notNull(e);//Comprobamos que no sea nulo
			Double c=dealService.avgApplyDeal();//Obtenemos la media de applyes por cada offer y request
			Assert.notNull(c);//Comprobamos que no sea nulo
			Collection<Customer>moreApplyAccepted=customerService.customerApplyAccepted();//Obtenemos los customer con mayor numero de applies aceptados
			Assert.notNull(moreApplyAccepted);//Comprobamos que no sea nulo
			Collection<Customer>moreApplyDenied=customerService.customerApplyDenied();//Obtenemos los customer con mayor numero de applies denegados
			Assert.notNull(moreApplyDenied);//Comprobamos que no sea nulo
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	/*DashBoard B
	 * Display a dashboard with the following information.
		- Average number of comments per actor, offer, or request.
		-Average number of comments posted by administrators and customers.
		- The actors who have posted ±10% the average number of comments per actor.

	 */
	@Test
	public void driverDashBoardB() {
		Object testingData[][] = {
			{"admin",null}, 
			{"customer1",IllegalArgumentException.class},
			{null,IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			templateDashBoardB((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	protected void templateDashBoardB(String user,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Double d=commentableService.avgComment();//Obtenemos la media de comentarios en las entidades comentables
			Assert.notNull(d);//Comprobamos que no sea nulo
			Double e=actorService.avgCommentByActor();//Obtenemos la media de comentarios por actor.
			Assert.notNull(e);//Comprobamos que no sea nulo
			Collection<Actor>moreThanTenPercent=actorService.actorAvgCommentPlusTenPercent();//Obtenemos usuarios que tienen una media de comentarios mayor al 10% de la media
			Assert.notNull(moreThanTenPercent);//Comprobamos que no sea nulo
			Collection<Actor>lessThanTenPercent=actorService.actorAvgCommentMinusTenPercent();//Obtenemos usuarios que tienen una media de comentarios menor al 10% de la media
			Assert.notNull(lessThanTenPercent);//Comprobamos que no sea nulo
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	/*DashBoard A
	 * Display a dashboard with the following information:
		- The minimum, the average, and the maximum number of messages sent per
		actor.
		- The minimum, the average, and the maximum number of messages received
		per actor.
		- The actors who have sent more messages.
		- The actors who have got more messages.
	 */
	@Test
	public void driverDashBoardA() {
		Object testingData[][] = {
			{"admin",null}, 
			{"customer1",IllegalArgumentException.class},
			{null,IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			templateDashBoardA((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	protected void templateDashBoardA(String user,Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			authenticate(user);//Nos autenticamos como usuario
			Collection<Double> d=actorService.minAvgMaxReceived();//Obtenemos minimo,maximo y la media de los mensajes recibidos
			Assert.notNull(d);//Comprobamos que no sea nulo
			Collection<Double> e=actorService.minAvgMaxSent();//Obtenemos minimo,maximo y la media de los mensajes enviados
			Assert.notNull(e);//Comprobamos que no sea nulo
			
			Collection<Actor>moreMeesagesSent=actorService.actorSentMoreMessage();//Obtenemos el actor que mas mensajes ha enviado
			Assert.notNull(moreMeesagesSent);//Comprobamos que no sea nulo
			Collection<Actor>moreMeesagesReceived=actorService.actorReceivedMoreMessage();//Obtenemos el actor que mas mensajes ha recibido
			Assert.notNull(moreMeesagesReceived);//Comprobamos que no sea nulo
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
}
