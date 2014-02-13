package carlota.votenofilme.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import carlota.components.database.dao.DAO;
import carlota.components.utils.Logger;
import carlota.components.utils.Utils;
import carlota.votenofilme.KeyNames;
import carlota.votenofilme.criterias.MoreRelationalCriteria;
import carlota.votenofilme.criterias.RelationIdCriteria;
import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.database.models.RelatedVote;

public class VoteServices{

	private static VoteServices voteServices;
	private DAO<Movie> dao;
	private DAO<RelatedVote> daoRV;
	
	private List<Movie> movies;
	private List<RelatedVote> relatedVote;
	
	//return the first two movies to be voted
	public List<Movie> getFirsts(){
		List<Movie> movies = new ArrayList<>();
		int idx = Utils.getRand(getMovies().size());
		int next;

		do{
			next = Utils.getRand(getMovies().size());
		}while(next == idx);
		
		movies.add(getMovies().get(idx));
		movies.add(getMovies().get(next));
		
		return movies;
	}
	
	//return the next two movies to be voted
	@SuppressWarnings("unchecked")
	public List<Movie> getNext(Map<String, Object> session){
		List<Integer> voted = (List<Integer>) session.get(KeyNames.VOTED);
		List<Integer> notVoted = (List<Integer>) session.get(KeyNames.NOT_VOTED);
		
		MoreRelationalCriteria mr = new MoreRelationalCriteria();
		
		return mr.meetCriteria(getRelatedVote(), voted, notVoted);
	}
	
	public void makeRelations(List<Integer> voted){
		if(voted.size() > 1){
			Integer newId = voted.get(voted.size()-1);
			RelationIdCriteria ric = new RelationIdCriteria();
			for(int i = 0; i < voted.size() - 1; i++){
				RelatedVote rv = ric.meetCriteria(getRelatedVote(), newId, voted.get(i));
				rv.setAmount(rv.getAmount() + 1);
				getDaoRV().update(rv);
			}
		}
	}
	
	//[TODO] vai dar pau de detachedentity ctza!
	public void computeVote(Integer id){
		Movie movie = null;
		for(Movie m : getMovies())
			if(m.getId().intValue() == id.intValue()){
				movie = m;
				break;
			}
		Logger.getInstance().logInfo("Updating votes for Movie " + movie.getId(), VoteServices.class);
		movie.setVotes(movie.getVotes() + 1);
		getDao().update(movie);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
	}
	
	private VoteServices() throws NoResultException{
		init();
	}
	
	public static VoteServices getInstance() throws NoResultException{
		if(voteServices == null){
			voteServices = new VoteServices();
		}
		return voteServices;
	}
	
	private void init() throws NoResultException{
		Logger.getInstance().logInfo("Initializing VoteServices", VoteServices.class);
		movies = getDao().listAll();
		relatedVote = getDaoRV().listAll();
		repopulateRelatedVotes();
		if(getMovies().isEmpty() || getMovies().size() < 2){
			Logger.getInstance().logSevere("Could not initialize VoteServices - no movies avaliable", VoteServices.class);
			throw new NoResultException();
		}
		Logger.getInstance().logInfo("VoteServices has been initialized successfully", VoteServices.class);
	}

	private void repopulateRelatedVotes(){
		for(RelatedVote rv : getRelatedVote()){
			rv.setMovieOne(getMovieById(rv.getMovieOne().getId()));
			rv.setMovieTwo(getMovieById(rv.getMovieTwo().getId()));
		}
	}
	
	private Movie getMovieById(Integer id){
		for(Movie m : getMovies()){
			if(m.getId().intValue() == id.intValue())
				return m;
		}
		return null;
	}
	
	private List<Movie> getMovies() {
		return movies;
	}
	
	private DAO<Movie> getDao() {
		if(dao == null)
			dao = new DAO<Movie>(Movie.class);
		return dao;
	}
	
	private DAO<RelatedVote> getDaoRV(){
		if(daoRV == null)
			daoRV = new DAO<RelatedVote>(RelatedVote.class);
		return daoRV;
	}
	
	private List<RelatedVote> getRelatedVote() {
		return relatedVote;
	}
	
	public List<Movie> getVotes(){
		List<Movie> l = new ArrayList<>();
		l.addAll(getMovies());
		Collections.sort(l, Utils.MOVIES_VOTES_COMPARATOR);
		return l;
	}

}