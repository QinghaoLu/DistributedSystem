package dsblockchain;

import java.io.Serializable;
import java.util.ArrayList;

public class Blockchain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String voting_info;
	private  int numOfSelection;
	private ArrayList<String> voting_options;
	private ArrayList<Block> blockchain;
	
	public Blockchain( String voting_info,int numOfSelection, ArrayList<String> voting_options,String hostUser) {
		this.numOfSelection= numOfSelection;
		this.voting_info=voting_info;
		this.voting_options= new ArrayList<String>();
		this.blockchain=new ArrayList<Block>();
		int [] empty= {-1};
		blockchain.add(new Block(hostUser,"initialdata","0",empty));
		
	}
	
	public void addBlock(Block b) {
		this.blockchain.add(b);
		
	}
	public ArrayList<Block> getBlockchain() {
		return this.blockchain;
	}
	
	public boolean verifyUser(String user) {
		boolean hasVoted=false;
		for(int i=0;i<this.blockchain.size();i++) {
			if(user.equals(this.blockchain.get(i).getUser())) {
				hasVoted=true;
			}else
				continue;
		}
		return hasVoted;
	}
	
	public String getVotingInfo() {
		return this.voting_info;
	}
	public ArrayList<String> getVotingOptions() {
		return this.voting_options;
	}
	
	public int getNumOfSelection() {
		return this.numOfSelection;
	}
	
	
	public int [] getVotes() {
		int [] result = new int[this.numOfSelection];
		for(int i=1;i<this.blockchain.size();i++) {
			for(int j=0;j<this.blockchain.get(i).getSelection().length;j++) {
				result[this.blockchain.get(i).getSelection()[j]]++;
			}
		
		}
		return result;
	}
	
}
