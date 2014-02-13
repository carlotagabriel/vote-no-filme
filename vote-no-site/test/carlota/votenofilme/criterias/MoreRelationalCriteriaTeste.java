package carlota.votenofilme.criterias;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import carlota.components.database.dao.DAO;
import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.database.models.RelatedVote;
import carlota.votenofilme.utils.Utils;

public class MoreRelationalCriteriaTeste {
	private static DAO<RelatedVote> dao;
	private static MoreRelationalCriteria criteria;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new DAO<RelatedVote>(RelatedVote.class);
		criteria = new MoreRelationalCriteria();
	}

	/*
	 *  votado -> 1 
	 *  nao votado -> 3
	 *  mostrar ainda 2, 4 e 5...
	 *  entre os 3 acima, escolher os dois mais votados de quem votou no 1
	 *  
	 *  Verifica se ele escolhe corretamente as opcoes da segunda votacao...
	 *  ela eh dada por conhecimento de votos relacionados...
	 *  se quem votou no filme 1 votar na sequencia no filme 2... eles sao relacionados
	 *  o valor da relacao se dá em quantas vezes o fato acima ocorre
	 */
	@Test
	public void testSegundaOpcao() {
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
		
		List<Movie> retorno = criteria.meetCriteria(votes, voted, notVoted);
		
		if(retorno.size() == 2){
			Movie m1 = retorno.get(0);
			Movie m2 = retorno.get(1);
			System.out.println("size: " + retorno.size() + " | id1: " + m1.getId() + 
				" | id2: " + m2.getId());
			Assert.assertTrue((m1.getId().intValue() == acerto1 || m1.getId().intValue() == acerto2) && 
						(m2.getId().intValue() == acerto1 || m2.getId().intValue() == acerto2));
		}
	}
	
	/*
	 * Deve apresentar o filme que ainda nao foi apresentado
	 * e por fim escolher entre os outros dois nao votados que possuem mais relacao com os dois votados
	 * anteriormente...
	 */
	@Test
	public void testUltimaOpcao() {
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

		List<Movie> retorno = criteria.meetCriteria(votes, voted, notVoted);
		
		if(retorno.size() == 2){
			Movie m1 = retorno.get(0);
			Movie m2 = retorno.get(1);
			System.out.println("size: " + retorno.size() + " | id1: " + m1.getId() + 
				" | id2: " + m2.getId());
			Assert.assertTrue((m1.getId().intValue() == acerto1 || m1.getId().intValue() == acerto2) && 
						(m2.getId().intValue() == acerto1 || m2.getId().intValue() == acerto2));
		}
	}
}
