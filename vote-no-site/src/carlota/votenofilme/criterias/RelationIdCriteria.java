package carlota.votenofilme.criterias;

import java.util.List;

import carlota.votenofilme.database.models.RelatedVote;

public class RelationIdCriteria {

	public RelatedVote meetCriteria(List<RelatedVote> relatedVotes, Integer firstId, Integer secondId){
		for(RelatedVote rv : relatedVotes){
			if((rv.getMovieOne().getId().intValue() == firstId.intValue() && 
					rv.getMovieTwo().getId().intValue() == secondId.intValue()) || 
					(rv.getMovieTwo().getId().intValue() == firstId.intValue() && 
					rv.getMovieOne().getId().intValue() == secondId.intValue()))
				return rv;
		}
		return null;
	}
	
}
