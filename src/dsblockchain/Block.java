package dsblockchain;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private long timeStamp;
	private String data;
	private String voting_info;
	public  int numOfSelection;
	private ArrayList<String> voting_options;
	
	// Constructor for first block
	public Block(String data,String voting_info,int numOfSelection, ArrayList<String> voting_options) {
		this.data=data;
		this.numOfSelection= numOfSelection;
		this.voting_info=voting_info;
		this.voting_options= new ArrayList<String>();
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
		
	}
	//constructor for the following block
	public Block(String data,String previousHash,String option) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) + 
				data 
				);
		return calculatedhash;
	}
	
	
	
	public static void main(String[]args) {
		
	}
}
