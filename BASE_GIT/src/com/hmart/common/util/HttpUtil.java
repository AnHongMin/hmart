package com.hmart.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public static String getHttpResult(String Url){
		org.apache.commons.httpclient.HttpClient client = null;
		org.apache.commons.httpclient.methods.GetMethod get = null;
		BufferedReader br = null;
		String line ="";//read line
		String sResult = "";
		try{
			client = new org.apache.commons.httpclient.HttpClient(new org.apache.commons.httpclient.MultiThreadedHttpConnectionManager());
			client.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
			get = new org.apache.commons.httpclient.methods.GetMethod(Url);
			get.setFollowRedirects(true);
			int iGetResultCode = client.executeMethod(get);
			if(iGetResultCode == org.apache.commons.httpclient.HttpStatus.SC_OK){
				br = new BufferedReader( new InputStreamReader(get.getResponseBodyAsStream(), "UTF-8")); 
				while((line = br.readLine()) != null ){
					sResult += line;
				}
			}else{
				sResult = String.valueOf(iGetResultCode);
			}
		}catch(org.apache.commons.httpclient.ConnectTimeoutException cte){
			sResult = cte.toString();
		}catch(Exception e){
			sResult = e.toString();
		}finally{
			if(get != null)	get.releaseConnection();
		}
		return sResult;
	}
	
	public static String sendHttpConnection(String Url){
		HttpURLConnection hconn = null;
		URL urlUserInterface = null; 
		BufferedReader br = null;
		String line ="";//read line
		String sResult = "";
		try {
			urlUserInterface = new URL(Url);			
			hconn = (HttpURLConnection)urlUserInterface.openConnection(); 
			hconn.setRequestMethod("GET"); 
			hconn.disconnect(); 
			br = new BufferedReader( new InputStreamReader(hconn.getInputStream(), "UTF-8")); 
			while((line = br.readLine()) != null ){
				sResult += line;
			}
		} catch(java.lang.Exception ex){
		} finally {
			if (br != null ) br=null; 
			hconn.disconnect();
		}
		return sResult;
	}
}
