package carlota.votenofilme.services;

import javax.persistence.NoResultException;

import carlota.components.database.dao.DAO;
import carlota.components.utils.Logger;
import carlota.votenofilme.database.models.User;

public class UserServices {
	private static UserServices userServices;
	private DAO<User> dao;

	public void saveUser(User u){
		getDao().create(u);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}
	
	private UserServices(){
		init();
	}
	
	public static UserServices getInstance() throws NoResultException{
		if(userServices == null){
			userServices = new UserServices();
		}
		return userServices;
	}
	
	private void init() throws NoResultException{
		Logger.getInstance().logInfo("UserServices has been initialized successfully", VoteServices.class);
	}
 
	private DAO<User> getDao() {
		if(dao == null)
			dao = new DAO<User>(User.class);
		return dao;
	}
}
