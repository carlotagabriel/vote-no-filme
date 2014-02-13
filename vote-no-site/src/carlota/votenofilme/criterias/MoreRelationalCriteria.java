package carlota.votenofilme.criterias;

import java.util.ArrayList;
import java.util.List;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.database.models.RelatedVote;

public class MoreRelationalCriteria {
	
	private static String query = "(id_movie_1 = ?1 AND (id_movie_2 = ?2 OR "
			+ "id_movie_2 = ?3)) OR (id_movie_2 = ?1 AND (id_movie_1 = ?2 OR "
			+ "id_movie_1 = ?3))";


	public List<Movie> meetCriteria(List<RelatedVote> relatedVotes, List<Integer> voted, List<Integer> notVoted){

		RelatedVote rv1 = new RelatedVote();
		RelatedVote rv2 = new RelatedVote();
		List<Movie> l = new ArrayList<Movie>();

		if(voted.size() < 2){
			//se votou apenas em um pega os dois que tiveram mais votos quando o votado do momento também foi votado no momento dos outros votos
			for(Integer m : voted){
				for(RelatedVote rv : relatedVotes){
					if(!isNotVoted(rv, notVoted) && isVoted(rv, m)){
						getMost(rv, rv1, rv2);
					}
				}
			}
			
			if(rv1 != null)
				l.add(getMovie(voted, rv1));
			
			if(rv2 != null)
				l.add(getMovie(voted, rv2));

		}else{
			//se for o ultimo voto, deve apresentar aquele q ainda nao foi votado e o com mais votos somando os outros dois votados
			
			DAO<RelatedVote> dao = new DAO<RelatedVote>(RelatedVote.class);
			
			List<RelatedVote> list1 = dao.list(query, notVoted.get(0), voted.get(0), voted.get(1));
			List<RelatedVote> list2 = dao.list(query, notVoted.get(1), voted.get(0), voted.get(1));
			
			int count1 = list1.get(0).getAmount() + list1.get(1).getAmount();
			int count2 = list1.get(0).getAmount() + list2.get(1).getAmount();
			
			rv1.setMainOptions(count1 > count2 ? list1.get(0) : list2.get(0));
			l.add(getMovie(voted, rv1));
			
			String q = "id NOT IN (" + voted.get(0) + ", " + + voted.get(1) + ", " + 
					+ notVoted.get(0) + ", " + + notVoted.get(1) + ")";
			
			DAO<Movie> daoM = new DAO<Movie>(Movie.class);
			l.add(daoM.find(q));
		}
		
		return l;
	}
	
	private void getMost(RelatedVote rv, RelatedVote rv1, RelatedVote rv2){
		if(rv1.getId() != null && rv2.getId() != null){
			if(rv.getAmount().intValue() > rv1.getAmount().intValue()){
				if(rv1.getAmount().intValue() > rv2.getAmount().intValue()){
					rv2.setMainOptions(rv);
				}else{
					rv1.setMainOptions(rv);
				}
			}else if(rv.getAmount().intValue() > rv2.getAmount().intValue()){
				rv2.setMainOptions(rv);
			}
		}else{
			if(rv1.getId() != null){
				rv2.setMainOptions(rv);
			}else{
				rv1.setMainOptions(rv);
			}
		}
	}
	
	private boolean isVoted(RelatedVote rv, Integer voted){
		if(rv.getMovieOne().getId().intValue() == voted.intValue()
				|| rv.getMovieTwo().getId().intValue() == voted.intValue())
			return true;
		
		return false;
	}
	
	private boolean isNotVoted(RelatedVote rv, List<Integer> notVoted){
		for(Integer not : notVoted)
			if(rv.getMovieOne().getId().intValue() == not.intValue()
					|| rv.getMovieTwo().getId().intValue() == not.intValue())
				return true;
		return false;
	}
	
	private Movie getMovie(List<Integer> voted, RelatedVote rv){
		for(Integer m : voted)
			if(rv.getMovieOne().getId().intValue() == m.intValue())
				return rv.getMovieTwo();
			else if(rv.getMovieTwo().getId().intValue() == m.intValue())
				return rv.getMovieOne();
		
		return null;
	}
	
}
