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

	public static UserInfo registration(String portNum) {

		Scanner scanner = new Scanner(System.in);
		try {
			Registry regist = LocateRegistry.getRegistry();
			directoryInterface fi = (directoryInterface) regist.lookup("directoryInterface");
			String ipaddr;
			ipaddr = InetAddress.getLocalHost().toString();
			UserInfo userInfo;

			while (true) {
				System.out.println("Enter 1 to register, enter 2 to log in");

				String check = scanner.nextLine();
				if (check.equals("1")) {
					System.out.println("Enter username: ");
					String rUsername = scanner.nextLine();
					System.out.println("Enter password: ");
					String rPasswd = scanner.nextLine();

					boolean rSucess = fi.registerfunc(rUsername, rPasswd, ipaddr, portNum);
					if (rSucess) {
						System.out.println("Regist success.");

					} else {
						System.out.println("Regist failed.");
					}

				} else if (check.equals("2")) {
					System.out.println("Enter username: ");
					String lUsername = scanner.nextLine();
					System.out.println("Enter password: ");
					String lPasswd = scanner.nextLine();
					boolean lSucess = fi.loginfunc(lUsername, lPasswd, ipaddr, portNum);
					if (lSucess) {
						scanner.close();
						System.out.println("Log in success.");
						userInfo = new UserInfo(lUsername, lPasswd, ipaddr, portNum);
						return userInfo;
					} else {
						System.out.println("Log in failed.");
					}

				} else {

					System.out.println("Please enter 1 or 2.");

				}

			}
		} catch (Exception e) {
			System.out.println("Something going wrong. ");
		}

		return null;

	}

}
