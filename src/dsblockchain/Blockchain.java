package dsblockchain;

import java.util.ArrayList;

public class Blockchain {
	private String voting_info;
	public  int numOfSelection;
	private ArrayList<String> voting_options;
	public ArrayList<Block> blockchain;
	
	public Blockchain( String voting_info,int numOfSelection, ArrayList<String> voting_options,String hostUser) {
		this.numOfSelection= numOfSelection;
		this.voting_info=voting_info;
		this.voting_options= new ArrayList<String>();
		this.blockchain=new ArrayList<Block>();
		blockchain.add(new Block(hostUser,"initialdata","0"));
		
	}
	
	public void addBlock(Block b) {
		this.blockchain.add(b);
		
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
	
	
}
