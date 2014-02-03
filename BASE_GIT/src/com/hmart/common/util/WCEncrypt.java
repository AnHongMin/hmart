package com.hmart.common.util;

import java.util.ArrayList;

public class WCEncrypt {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String Encrypt(String Text){
		String output = new String();
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();
		for(int i=0; i<Text.length(); i++){
			long rnd = Math.round(Math.random()*122)+68;
			al1.add(String.valueOf(Text.charAt(i)+rnd));
			al2.add(String.valueOf(rnd));
		}
		for(int j=0; j<al1.size(); j++){
			String i1 = (String)al1.get(j);
			String i2 = (String)al2.get(j);
			output += (char)Integer.parseInt(i1);
			output += (char)Integer.parseInt(i2);
		}
		return output;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String unEncrypt(String Text){
		char[] chVal;
		int outVal = 0;
		int intVal = 0;
		String output = new String();
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();
		chVal = Text.toCharArray();	
		for(int j = 0; j < chVal.length; j++){
			intVal = chVal[j];
			al1.add(String.valueOf(intVal));
		}
		for(int j = 1; j < chVal.length+1; j++){
			if(j==chVal.length){
				intVal = 0;	
			}else{
				intVal = chVal[j];
			}
			al2.add(String.valueOf(intVal));
		}
		for(int i=0; i<al1.size(); i=i+2){
			String i1 = (String)al1.get(i);
			String i2 = (String)al2.get(i);
			if(al1.size()-1 !=i){
				outVal = Integer.parseInt(i1)-Integer.parseInt(i2);			
				output += (char)outVal;
			}
		}
		return output;
	}
}
