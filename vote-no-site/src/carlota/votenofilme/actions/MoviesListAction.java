package carlota.votenofilme.actions;

import java.util.List;

import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.services.VoteServices;

import com.opensymphony.xwork2.Action;

public class MoviesListAction implements Action{
	private List<Movie> orderedMovies;
	
	@Override
	public String execute() throws Exception {
		setOrderedMovies(VoteServices.getInstance().getVotes());
	
		return SUCCESS;
	}
	
	public List<Movie> getOrderedMovies() {
		return orderedMovies;
	}
	
	public void setOrderedMovies(List<Movie> orderedMovies) {
		this.orderedMovies = orderedMovies;
	}
	
}
