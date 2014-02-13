package carlota.votenofilme.database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import carlota.components.database.entity.DefaultModel;

@Entity
@Table(name="movies")
public class Movie extends DefaultModel{

	private static final long serialVersionUID = 4768193759960665300L;

	private String name;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column(name="image_path")
	private String imagePath;
	
	@Column(columnDefinition = "int default 0")
	private Integer votes;
	
	public Integer getVotes() {
		return votes;
	}
	
	public void setVotes(Integer votes) {
		this.votes = votes;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
