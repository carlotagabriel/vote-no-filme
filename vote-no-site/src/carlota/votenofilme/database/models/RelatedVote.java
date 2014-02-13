package carlota.votenofilme.database.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import carlota.components.database.entity.DefaultModel;

@Entity
@Table(name="related_votes")
public class RelatedVote extends DefaultModel{

	private static final long serialVersionUID = 2794936941744570153L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movie_1", referencedColumnName = "id")
	private Movie movieOne;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_movie_2", referencedColumnName = "id")
	private Movie movieTwo;
	
	@Column(columnDefinition = "int default 0")
	private Integer amount;
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Movie getMovieOne() {
		return movieOne;
	}

	public void setMovieOne(Movie movieOne) {
		this.movieOne = movieOne;
	}

	public Movie getMovieTwo() {
		return movieTwo;
	}

	public void setMovieTwo(Movie movieTwo) {
		this.movieTwo = movieTwo;
	}
	
	public void setMainOptions(RelatedVote old){
		setAmount(old.getAmount());
		setId(old.getId());
		setMovieOne(old.getMovieOne());
		setMovieTwo(old.getMovieTwo());
	}

	@Override
	public String toString() {
		return "M1: " + getMovieOne().getId() + " | M2: " + getMovieTwo().getId() + " | " + getAmount();
	}
}
