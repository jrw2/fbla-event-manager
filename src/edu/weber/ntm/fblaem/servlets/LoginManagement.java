package edu.weber.ntm.fblaem.servlets;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServlet;

public class LoginManagement  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3008678826075492782L;
	public LoginManagement()
	{
		super();
	}
	
	
	public static String hash(String password) throws NoSuchAlgorithmException {
		 try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
	            byte[] digest = md.digest();
	            
	            BigInteger bigInt = new BigInteger(1, digest);
	            StringBuffer sb = new StringBuffer();
	            for (int i = 0; i < digest.length; i++) {
	                String hex = Integer.toHexString(0xff & digest[i]);
	                if (hex.length() == 1) sb.append('0');
	                sb.append(hex);
	            }
	            System.out.println(sb.toString());
	            return sb.toString();
	 
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	 
	        }
		 return null;
	}

}
