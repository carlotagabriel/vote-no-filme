package carlota.votenofilme.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import carlota.components.utils.Utils;
import carlota.votenofilme.KeyNames;
import carlota.votenofilme.database.models.Movie;
import carlota.votenofilme.services.VoteServices;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport{

	private static final long serialVersionUID = -303721586115449373L;

	private Integer optionId;
	
	private VoteServices voteServices;
	
	private static String TAG_NEXT_VOTE = "next_vote";
	private static String TAG_USER_INFO = "requestUserInfo";

	@SuppressWarnings("unchecked")
	public String execute(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(optionId != null){
			if(session.containsKey(KeyNames.VOTED) && ((List<Integer>) session.get(KeyNames.VOTED)).size() > 2){
				return TAG_USER_INFO;
			}

			putAttribute(session, KeyNames.VOTED, optionId);
			putNotVoted(session);
			
			List<Integer> votes = (List<Integer>) session.get(KeyNames.VOTED);
			
			getVoteServices().computeVote(optionId);
			getVoteServices().makeRelations(votes);
			
			if(votes.size() > 2){
				return TAG_USER_INFO;
			}
			
			List<Movie> next = getVoteServices().getNext(session);
			
			session.remove(KeyNames.OPTIONS_MAP);
			session.put(KeyNames.OPTIONS_MAP, next);
			return TAG_NEXT_VOTE;
		}else{
			Utils.clearSessions(session);
			List<Movie> m = VoteServices.getInstance().getFirsts();
			session.put(KeyNames.OPTIONS_MAP, m);
		}
		
		return SUCCESS;
	}

	//prepare the voted atribute with news itens
	@SuppressWarnings("unchecked")
	private void putAttribute(Map<String, Object> session, String attribute, Integer id){
		if(session.containsKey(attribute))
			((List<Integer>)session.get(attribute)).add(id);
		else{
			List<Integer> l = new ArrayList<Integer>();
			l.add(id);
			session.put(attribute, l);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void putNotVoted(Map<String, Object> session){
		List<Movie> m = (List<Movie>) session.get(KeyNames.OPTIONS_MAP);
		if(m.get(0).getId().intValue() == optionId.intValue())
			putAttribute(session, KeyNames.NOT_VOTED, m.get(1).getId());
		else
			putAttribute(session, KeyNames.NOT_VOTED, m.get(0).getId());
	}
	
	public Integer getOptionId() { 
		return optionId;
	}
	
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	
	private VoteServices getVoteServices() {
		if(voteServices == null)
			voteServices = VoteServices.getInstance();
		return voteServices;
	}
	
}
