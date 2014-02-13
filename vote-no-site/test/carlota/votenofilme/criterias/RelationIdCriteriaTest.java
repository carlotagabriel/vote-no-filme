package carlota.votenofilme.criterias;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.database.models.RelatedVote;

public class RelationIdCriteriaTest {
	private static RelationIdCriteria criteria;
	private static DAO<RelatedVote> dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		criteria = new RelationIdCriteria();
		dao = new DAO<RelatedVote>(RelatedVote.class);
	}

	/*
	 * método que apenas encontra o RelatedVote que possuí os dois ids passados
	 */
	@Test
	public void testMeetCriteria() {
		List<RelatedVote> list = dao.listAll();
		
		int id1 = 4;
		int id2 = 5;
		
		RelatedVote rv = criteria.meetCriteria(list, id1, id2);
		
		Assert.assertTrue((rv.getMovieOne().getId().intValue() == id1 && 
				rv.getMovieTwo().getId().intValue() == id2) || 
				(rv.getMovieTwo().getId().intValue() == id1 && 
				rv.getMovieOne().getId().intValue() == id2));
		
	}

}
