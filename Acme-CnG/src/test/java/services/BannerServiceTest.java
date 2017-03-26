package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class BannerServiceTest extends AbstractTest{
	// The SUT -----------------------------------------------

		@Autowired
		private BannerService	bannerService;

		// Tests --------------------------------------------------

		/* An admin must able to change the banner that the system shows on the welcome page.. 
		 * 
		 * Para ello primero vamos a probar crear un banner con un admin y con un customer.*/
		@Test
		public void driverCreate() {
			Object testingData[][] = {
					{"admin",null},
					{"customer1",IllegalArgumentException.class}
			};
			for(int i=1;i<testingData.length;i++){
				templateCreate((String) testingData[i][0], (Class<?>)testingData[i][1]);
				
			}
		}
		protected void templateCreate(String username, Class<?> expected) {
			Class<?> caught;

			caught = null;
			try {
				authenticate(username); // Nos autenticamos como el usuario
				bannerService.create(); // Intentamos crear un banner nuevo.
				unauthenticate();
			} catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
		}
		
		/*Edit banner
		 * 
		 *Vamos a probar editar un banner con un administrador y con un customer*/
		@Test
		public void driverEdit() {
			Object testingData[][] = {
					{"admin",41,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg",null},
					{"customer1",41,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg",IllegalArgumentException.class}
			};
			for(int i=1;i<testingData.length;i++){
				templateEdit((String) testingData[i][0],(int) testingData[i][1],(String) testingData[i][2],(Class<?>)testingData[i][3]);
				
			}
		}
		protected void templateEdit(String username, int id,String img,Class<?> expected) {
			Class<?> caught;

			caught = null;
			try {
				authenticate(username); // Nos autenticamos como el usuario
				Banner banner=bannerService.findOne(id); // Buscamos el banner a editar.
				banner.setImage(img);//Cambiamos la imagen que aparece en el banner
				Banner b=bannerService.save(banner);//Guardamos los cambios
				Assert.notNull(b);
				Assert.isTrue(b.getImage().equals(img));
				unauthenticate();
			} catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
		}
		/*Delete banner
		 * 
		 *Vamos a probar eliminar un banner con un administrador y con un customer*/
		@Test
		public void driverDelete() {
			Object testingData[][] = {
					{"admin",41,null},
					{"customer1",41,IllegalArgumentException.class}
			};
			for(int i=1;i<testingData.length;i++){
				templateDelete((String) testingData[i][0],(int) testingData[i][1],(Class<?>)testingData[i][2]);
				
			}
		}
		protected void templateDelete(String username, int id,Class<?> expected) {
			Class<?> caught;

			caught = null;
			try {
				authenticate(username); // Nos autenticamos como el usuario
				Banner banner=bannerService.findOne(id); // Buscamos el banner a borrar.
				Assert.notNull(banner);
				bannerService.delete(banner);//Eliminamos el banner
				Banner banner2=bannerService.findOne(id);
				Assert.isNull(banner2);
				unauthenticate();
			} catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
		}
		
		/*Make banner principal
		 * 
		 *Vamos a probar hacer principal un banner con un administrador y con un customer*/
		@Test
		public void driverMakePrincipal() {
			Object testingData[][] = {
					{"admin",41,null},
					{"customer1",41,IllegalArgumentException.class}
			};
			for(int i=1;i<testingData.length;i++){
				templateMakePrincipal((String) testingData[i][0],(int) testingData[i][1],(Class<?>)testingData[i][2]);
				
			}
		}
		protected void templateMakePrincipal(String username, int id,Class<?> expected) {
			Class<?> caught;

			caught = null;
			try {
				authenticate(username); // Nos autenticamos como el usuario
				Banner banner=bannerService.findOne(id); // Buscamos el banner a borrar.
				Assert.notNull(banner);
				bannerService.makePrincipal(banner);//Hacemos principal el banner
				Assert.isTrue(banner.getIsPrincipal());//Comprobamos que esta como principal
				unauthenticate();
			} catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
		}
}			
