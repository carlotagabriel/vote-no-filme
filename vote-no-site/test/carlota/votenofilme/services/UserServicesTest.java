package carlota.votenofilme.services;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.database.models.User;

public class UserServicesTest {
	
	private static DAO<User> dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new DAO<User>(User.class);
	}

	//método utilizado para salvar usuário
	@Test
	public void testSaveUser() {
		User u = new User();
		u.setName("Nome");
		u.setEmailAdress("email@email.com");
		
		dao.create(u);
		
		Assert.assertNotNull(dao.find("name = ?1 AND email_adress = ?2", u.getName(), u.getEmailAdress()));
	}

}
