package dsblockchain;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private long timeStamp;
	private String user;
	private String data;

	

	//constructor for the following block
	public Block( String user,String data,String previousHash) {
		this.user =user;
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) + 
				user+
				data 
				);
		return calculatedhash;
	}
	public String getUser() {
		return this.user;
	}
	
	
	public static void main(String[]args) {
		
	}
}
