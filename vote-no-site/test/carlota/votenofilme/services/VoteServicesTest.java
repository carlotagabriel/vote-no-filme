package carlota.votenofilme.services;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.KeyNames;
import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.database.models.RelatedVote;
import carlota.votenofilme.utils.Utils;

public class VoteServicesTest {

	private static VoteServices voteServices;
	private static DAO<RelatedVote> dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new DAO<RelatedVote>(RelatedVote.class);
	}

	/*
	 * testa se pega dois filmes diferentes...
	 * os filmes inicialmente sao escolhidos de maneira randomica
	 */
	@Test
	public void testGetFirsts() {
		List<Movie> list = voteServices.getFirsts();
		if(list.size() == 2 && list.get(0).getId() == list.get(1).getId())
			fail("Dois filmes iguais");
		Assert.assertEquals(2, list.size());
	}

	/*
	 * método que deve retornar os filmes da segunda votação
	 * ele deve ser escolhido entre os 3 nao apresentados
	 * ele deve escolher os dois que possuem maior relacao com o filme votado
	 */
	@Test
	public void testGetNext() {
		int idEscolhido = 3;
		int idNaoVotado = 5;

		int outro1 = 1;
		int valor1 = 10;
		
		int outro2 = 2;
		int valor2 = 1;
		
		int outro3 = 4;
		int valor3 = 8;
		
		int acerto1 = 1;
		int acerto2 = 4;
		
		List<RelatedVote> votes = dao.listAll();
		List<Integer> voted = Arrays.asList(new Integer(idEscolhido));
		List<Integer> notVoted = Arrays.asList(new Integer(idNaoVotado));
		
		Utils.setUpVotes(votes, idEscolhido, outro1, valor1, dao);
		Utils.setUpVotes(votes, idEscolhido, outro2, valor2, dao);
		Utils.setUpVotes(votes, idEscolhido, outro3, valor3, dao);
		
		try {
			voteServices = VoteServices.getInstance();
		} catch (NoResultException e) {
		}

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(KeyNames.VOTED, voted);
		session.put(KeyNames.NOT_VOTED, notVoted);
		List<Movie> movies = voteServices.getNext(session);
		
		Movie m1 = movies.get(0);
		Movie m2 = movies.get(1);
		
		Assert.assertTrue((m1.getId().intValue() == acerto1 || m1.getId().intValue() == acerto2) && 
					(m2.getId().intValue() == acerto1 || m2.getId().intValue() == acerto2));
	}

	/*
	 * método que deve retornar os filmes da ultima votacao
	 * ele deve apresentar o que até entao nao foi apresentado
	 * e o filme nao votado com maior relacao... levando em conta as duas votacoes anteriores
	 */
	@Test
	public void testGetNextLasts() {
		int idEscolhido1 = 3;
		int idEscolhido2 = 4;

		int idNaoVotado1 = 1;
		int idNaoVotado2 = 5;

		int outro1 = 1;
		int outro2 = 5;
		
		//valor com o nao votado1
		int valor11 = 1;
		int valor21 = 3;
		
		//valor com o nao votado2
		int valor12 = 12;
		int valor22 = 15;
		
		int acerto1 = 2;
		int acerto2 = 5;
		
		List<RelatedVote> votes = dao.listAll();
		List<Integer> voted = Arrays.asList(new Integer(idEscolhido1), new Integer(idEscolhido2));
		List<Integer> notVoted = Arrays.asList(new Integer(idNaoVotado1), new Integer(idNaoVotado2));
		
		Utils.setUpVotes(votes, idEscolhido1, outro1, valor11, dao);
		Utils.setUpVotes(votes, idEscolhido1, outro2, valor21, dao);
		
		Utils.setUpVotes(votes, idEscolhido2, outro1, valor12, dao);
		Utils.setUpVotes(votes, idEscolhido2, outro2, valor22, dao);
		
		try {
			voteServices = VoteServices.getInstance();
		} catch (NoResultException e) {
		}

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(KeyNames.VOTED, voted);
		session.put(KeyNames.NOT_VOTED, notVoted);
		List<Movie> movies = voteServices.getNext(session);
		
		Movie m1 = movies.get(0);
		Movie m2 = movies.get(1);
		
		Assert.assertTrue((m1.getId().intValue() == acerto1 || m1.getId().intValue() == acerto2) && 
					(m2.getId().intValue() == acerto1 || m2.getId().intValue() == acerto2));
	}

	/*
	 * MÉTODO que deve criar a relacao entre o primeiro voto e o segundo
	 */
	@Test
	public void testFirstMakeRelations() {
		int idEscolhido = 4;
		int idEscolhido2 = 1;

		List<Integer> voted = Arrays.asList(new Integer(idEscolhido), new Integer(idEscolhido2));

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(KeyNames.VOTED, voted);

		String query = "(id_movie_1 = ?1 AND id_movie_2 = ?2) OR (id_movie_1 = ?2 AND id_movie_2 = ?1)";
		RelatedVote rv = dao.find(query, idEscolhido, idEscolhido2);
		
		voteServices.makeRelations(voted);
		
		RelatedVote rv2 = dao.find(query, idEscolhido, idEscolhido2);

		Assert.assertEquals((int)(rv.getAmount() + 1), (int)(rv2.getAmount()));
	}

	/*
	 * MÉTODO que deve criar a relacao entre o ultimo voto e os dois primeiros
	 */
	@Test
	public void testMakeRelations() {
		Integer idEscolhido = 5;
		Integer idEscolhido2 = 4;
		Integer idEscolhido3 = 1;

		List<Integer> voted = Arrays.asList(new Integer(idEscolhido), new Integer(idEscolhido2), new Integer(idEscolhido3));

		Map<String, Object> session = new HashMap<String, Object>();
		session.put(KeyNames.VOTED, voted);
// a relacao entre o primeiro votado e o segundo já foi feita...
		String query = "(id_movie_1 = ?1 AND id_movie_2 = ?2) OR (id_movie_1 = ?2 AND id_movie_2 = ?1)";
		RelatedVote rv12 = dao.find(query, idEscolhido, idEscolhido3);
		RelatedVote rv13 = dao.find(query, idEscolhido2, idEscolhido3);

		voteServices.makeRelations(voted);
		
		RelatedVote rv22 = dao.find(query, idEscolhido, idEscolhido3);
		RelatedVote rv23 = dao.find(query, idEscolhido2, idEscolhido3);
		//Assert.assertEquals((int)(rv11.getAmount() + 1), (int)(rv21.getAmount()));
		Assert.assertEquals((Integer)(rv12.getAmount() + 1), (Integer)(rv22.getAmount()));
		Assert.assertEquals((Integer)(rv13.getAmount() + 1), (Integer)(rv23.getAmount()));

	}

	@Test
	public void testComputeVote() {
		int idEscolhido = 3;
		DAO<Movie> daoM = new DAO<Movie>(Movie.class);
		Movie m = daoM.find(idEscolhido);
		voteServices.computeVote(idEscolhido);
		Movie m2 = daoM.find(idEscolhido); 
		Assert.assertEquals((Integer)(m.getVotes() + 1), m2.getVotes());
	}

	@Test
	public void testClone() {
		try {
			Assert.assertNull(voteServices.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * esse método apenas ordena os filmes conforme a sua quantidade de votos...
	 * ordem decrescente
	 */
	@Test
	public void testGetVotes() {
		boolean ok = true;
		Integer last = null;
		for(Movie m : voteServices.getVotes()){
			if(last == null)
				last = m.getVotes();
			else if(last.intValue() < m.getVotes()){
				ok = false;
				break;
			}
		}
		Assert.assertTrue(ok);
	}

}
