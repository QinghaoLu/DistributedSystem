package dsmain;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;
import java.net.InetAddress;

public class RegisterClass {

//	public static void main(String argv[]) {
//		registration("hello");
//	}

	public static String[] registration(String portNum) {

		try {

			Registry regist = LocateRegistry.getRegistry();
			directoryInterface fi = (directoryInterface) regist.lookup("directoryInterface");
			String ipaddr;
			ipaddr = InetAddress.getLocalHost().toString();
			

//			System.out.println("Enter 1 to register, enter 2 to log in");
			while (true) {
				Scanner scanner = new Scanner(System.in);
//				String check = scanner.nextLine();
//				if (check == "1") {
				System.out.println("Enter username: ");
				String rUsername = scanner.nextLine();
				System.out.println("Enter password: ");
				String rPasswd = scanner.nextLine();
				scanner.close();
				boolean rSucess = fi.registerfunc(rUsername, rPasswd, ipaddr, portNum);
				if (rSucess) {
					System.out.println("Regist success.");
					String[] registInfo ={rUsername, rPasswd, ipaddr, portNum};
					return registInfo;

				} else {
					System.out.println("Regist failed.");
				}

			}
		} catch (Exception e) {
			System.out.println("Something going wrong. ");
			return null;
		}
		
	}

	public static String[] logIn(String portNum) {

		try {
			Registry regist = LocateRegistry.getRegistry();
			directoryInterface fi = (directoryInterface) regist.lookup("directoryInterface");
			String ipaddr;
			ipaddr = InetAddress.getLocalHost().toString();
			while (true) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter username: ");
				String lUsername = scanner.nextLine();
				System.out.println("Enter password: ");
				String lPasswd = scanner.nextLine();
				scanner.close();
				boolean lSucess = fi.loginfunc(lUsername, lPasswd, ipaddr, portNum);
				if (lSucess) {
					System.out.println("Log in success.");
					String[] logInInfo = { lUsername, lPasswd, ipaddr, portNum };
					return logInInfo;
				} else {
					System.out.println("Log in failed.");
				}
			}
		} catch (Exception e) {
			System.out.println("Something going wrong. ");
			return null;
		}
	}
}

//					
//					
//			else if (check == "2") {
//					System.out.println("Enter username: ");
//					String lUsername = scanner.nextLine();
//					System.out.println("Enter password: ");
//					String lPasswd = scanner.nextLine();
//					boolean lSucess = fi.loginfunc(lUsername, lPasswd, ipaddr, portNum);
//					if (lSucess) {
//						System.out.println("Log in success.");
//						break;
//					} else {
//						System.out.println("Log in failed.");
//					}
//		
//				} else {
//
//					System.out.println("Please enter 1 or 2.");
//
//				}
//
//			}
//					}
//		} catch (Exception e) {
//			System.out.println("Something going wrong. ");
//		}
//		return a;
//	}
//
//}
