/**
 * 
 */
package dsserver;

/**
 * @author zwmsc
 *
 */
public class UserInfo {

	String name;
	String passwd;
	String addr;
	String port;
	
	public UserInfo(String name, String pwd, String addr, String port) {
		this.name=name;
		this.passwd=pwd;
		this.addr=addr;
		this.port=port;
	}
}
