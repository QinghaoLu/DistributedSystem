package dsblockchain;
import java.security.MessageDigest;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	
	public Block(String data,String previousHash ) {
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
