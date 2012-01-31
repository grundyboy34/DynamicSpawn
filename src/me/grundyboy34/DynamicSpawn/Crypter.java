package me.grundyboy34.DynamicSpawn;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;
import java.math.*;

public class Crypter {
	
	   public String Encoded;
	   public static String Output;
	    public String NumberCalc;	  
	   
	    public int getX(String input) {
	    	int a = Character.getNumericValue(encode(input).charAt(54)) + Character.getNumericValue(encode(input).charAt(36)) + Character.getNumericValue(encode(input).charAt(84) + Character.getNumericValue(encode(input).charAt(39)));
	    	return a * 100;
	    }
	    public int getZ(String input) {
	    	int b = Character.getNumericValue(encode(input).charAt(69)) + Character.getNumericValue(encode(input).charAt(97)) + Character.getNumericValue(encode(input).charAt(71) + Character.getNumericValue(encode(input).charAt(102)));
	    	return b * 100;
	    }
	  
	 public String encode(String input) {		
	       try {
	 Encoded = calculateHash(input,"MD2") + calculateHash(input,"SHA-256") + calculateHash(input,"SHA-1");
	       } catch(Exception e) {
e.printStackTrace(); 
	     }
		return Encoded;      
		
	 }
	    public ArrayList<Integer> getlist(String player) {
	    	ArrayList<Integer> points = new ArrayList<Integer>();	    
	    	points.add(0,getX(player));
	    	points.add(1,64);
	    	points.add(2,getZ(player));  
	    	return points;
	    }
		public String calculateHash(String Input, String algorithm) 
	               throws NoSuchAlgorithmException, UnsupportedEncodingException {
	            MessageDigest md;
	            md = MessageDigest.getInstance(algorithm);
	            byte[] hash = new byte[40];
	            md.update(Input.getBytes("iso-8859-1"), 0, Input.length());
				hash = md.digest();
	                        return convertToHex(hash);
	        }
	        public String convertToHex(byte[] data) {
	            StringBuffer buffer = new StringBuffer();
	            for (int i = 0; i < data.length; i++) {
	                int halfbyte = (data[i] >>> 4) & 0x0F;
	                int two_halfs = 0;
	                do {
	                    if ((0 <= halfbyte) && (halfbyte <=9))
	                        buffer.append((char) ('0' + halfbyte));
	                    else
	                        buffer.append((char) ('a' + (halfbyte - 10)));
	                    halfbyte = data[i] &0x0F;
	                } while(two_halfs++ <1);
	                    
	                }
	            return buffer.toString();
	            }	        
	    
	        }