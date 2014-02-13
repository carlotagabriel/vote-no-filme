package carlota.votenofilme.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import carlota.components.database.entity.DefaultModel;

@Entity
@Table(name="users")
public class User extends DefaultModel{

	private static final long serialVersionUID = 2799401730725605126L;

	private String name;
	
	@Column(name="email_adress")
	private String emailAdress;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmailAdress() {
		return emailAdress;
	}
	
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
}
