package carlota.votenofilme.utils;

import java.util.List;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.database.models.RelatedVote;

public class Utils {
	public static void setUpVotes(List<RelatedVote> votes, Integer id1, Integer id2, Integer valor, DAO<RelatedVote> dao){
		for(RelatedVote rv : votes){
			if((rv.getMovieOne().getId().intValue() == id1.intValue() || 
					rv.getMovieTwo().getId().intValue() == id1.intValue()) &&
					(rv.getMovieOne().getId().intValue() == id2.intValue() || 
					rv.getMovieTwo().getId().intValue() == id2.intValue())){
				rv.setAmount(valor);
				dao.update(rv);
				break;
			}
		}
	}

}
