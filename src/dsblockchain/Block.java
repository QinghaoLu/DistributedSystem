package dsblockchain;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private String voting_info;
	private ArrayList<String> voting_options;
	
	private long timeStamp;
	// Constructor for first block
	public Block( String data,String voting_info) {
		this.data=data;
		this.voting_info=voting_info;
		this.voting_options= new ArrayList<String>();
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
		Block genesis= new Block("I am the first","0");
		System.out.println("Hash for block 1 : "+ genesis.hash);
		
		Block second= new Block("I am the second",genesis.hash);
		System.out.println("Hash for block 2 : "+ second.hash);
		
		Block third= new Block("I am the third", second.hash);
		System.out.println("Hash for block 3 : "+ third.hash);
	}
}
