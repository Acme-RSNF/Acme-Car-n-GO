package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class AdministratorServiceTest extends AbstractTest{
	// The SUT -----------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	//Tests---------------------------------------------------
	/*Creacion de un administrador
	 * 
	 */
	@Test
	public void driverCreate(){
		Object testingData[][]={
				{"admin","username", "password", "password", "Nombre", "Apellidos","correo@gmail.com", "+34 965456321", null},
				{"customer1","username", "password", "password", "Nombre", "Apellidos","correo@gmail.com", "+34 965456321", IllegalArgumentException.class},
				{null,"username", "password", "password", "Nombre", "Apellidos","correo@gmail.com", "+34 965456321", IllegalArgumentException.class},
				
		};
		for (int i=1;i<testingData.length;i++){
			templateCreate((String) testingData[i][0],(String) testingData[i][1], (String) testingData[i][2],(String) testingData[i][3],(String) testingData[i][4],(String) testingData[i][5],(String) testingData[i][6],(String) testingData[i][7],(Class<?>) testingData[i][8]);
		}
	}
	
	protected void templateCreate(String user,String username, String password, String password2, 
			String name, String surname, String email, String phone, Class<?> expected){
		Class<?>caught;
		Administrator admin;
		
		caught=null;
		try{
			authenticate(user);//Nos autenticamos como usuario
			admin=administratorService.create();//Creamos un administrador y le pasamos los parametros
			admin.getUserAccount().setUsername(username);
			admin.getUserAccount().setPassword(password);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setEmail(email);
			admin.setPhone(phone);
			Assert.notNull(admin);//Comprobamos que no sea nulo
			unauthenticate();
		}catch(Throwable oops){
			caught=oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	//Tests---------------------------------------------------
		/*Edicion de un administrador
		 * 
		 */
	@Test
	public void driverEdit(){
		Object testingData[][]={
				{"admin",49,null},
				{"customer1",49,IllegalArgumentException.class},
				{null,49,IllegalArgumentException.class}
				
		};
		for (int i=1;i<testingData.length;i++){
			templateEdit((String) testingData[i][0],(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}
	protected void templateEdit(String user,int id, Class<?> expected){
		Class<?>caught;
		
		
		caught=null;
		try{
			authenticate(user);//Nos autenticamos como usuario
			Administrator admin=administratorService.findOne(id);
			admin.setName("nombreNuevo");
			Administrator admin2=administratorService.save(admin);//Guardamos el admin con el nombre modificado
			Assert.notNull(admin2);//Comprobamos que no sea nulo
			Assert.isTrue(admin2.getName().equals("nombreNuevo"));
			unauthenticate();
		}catch(Throwable oops){
			caught=oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	
	//Tests---------------------------------------------------
			/*Eliminar un administrador
			 * 
			 */
		@Test
		public void driverDelete(){
			Object testingData[][]={
					{"admin",49,null},
					{"customer1",49,IllegalArgumentException.class},
					{null,49,IllegalArgumentException.class}
					
			};
			for (int i=1;i<testingData.length;i++){
				templateEdit((String) testingData[i][0],(int) testingData[i][1], (Class<?>) testingData[i][2]);
			}
		}
		protected void templateDelete(String user,int id, Class<?> expected){
			Class<?>caught;
			
			
			caught=null;
			try{
				authenticate(user);//Nos autenticamos como usuario
				Administrator admin=administratorService.findOne(id);
				administratorService.delete(admin);//Eliminamos el administrador
				Assert.isNull(admin);//Comprobamos que no sea nulo
				unauthenticate();
			}catch(Throwable oops){
				caught=oops.getClass();
			}
			checkExceptions(expected, caught);
		}
}
