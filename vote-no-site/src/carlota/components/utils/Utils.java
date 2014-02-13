package carlota.components.utils;

import java.util.Comparator;
import java.util.Map;

import carlota.votenofilme.KeyNames;
import carlota.votenofilme.database.models.Movie;

public class Utils {

	public static final EmailValidator emailValidator = new EmailValidator();
	
	public static int getRand(int maxValue){
		return (int) (Math.random() * (maxValue-1));
	}
	
	public static void clearSessions(Map<String, Object> session){
		session.remove(KeyNames.OPTIONS_MAP);
		session.remove(KeyNames.NOT_VOTED);
		session.remove(KeyNames.VOTED);
	}

	public static Comparator<Movie> MOVIES_VOTES_COMPARATOR = new Comparator<Movie>() {

		public int compare(Movie m1, Movie m2) {
	        if (m1.getVotes() > m2.getVotes()) {
	            return -1;
	        } else if (m1.getVotes() < m2.getVotes()) {
	            return 1;
	        } else {
	            return 0;
	        }        
		}

	};

}
