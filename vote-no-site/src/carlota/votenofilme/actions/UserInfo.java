package carlota.votenofilme.actions;

import java.util.Map;

import carlota.components.utils.Utils;
import carlota.votenofilme.database.models.User;
import carlota.votenofilme.services.UserServices;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserInfo extends ActionSupport implements ModelDriven<User>{

	private static final long serialVersionUID = 6231523085708140659L;

	private User user = new User();
	private Boolean error = false;
	
	@Override
	public void validate() {
		if(user.getName() == null || user.getName().equals("")){
			addFieldError("user.name", "O campo Nome é obrigatório.");
			setError(true);
		}
		
		if(user.getEmailAdress() == null || !Utils.emailValidator.validate(user.getEmailAdress())){
			addFieldError("user.emailAdress", "É necessário digitar um endereço de E-Mail válido.");
			setError(true);
		}

	}
	
	public String execute(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		setError(false);
		Utils.clearSessions(session);
		UserServices.getInstance().saveUser(getModel());

		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Boolean isError(){
		return error;
	}
	
	public void setError(Boolean error) {
		this.error = error;
	}

	@Override
	public User getModel() {
		return user;
	}
	
}
