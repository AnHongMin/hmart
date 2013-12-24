package com.hmart.common.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 
 * 웹요청 정보를 Console에 출력합니다. 웹요청의 헤더값과 파라미터 값을 Console에 출력합니다. 
 * 
 * @author 안홍민
 * @since 
 * @version 0.0.1
 */
public class RequestPrint {

	static Logger logger = Logger.getLogger(RequestPrint.class);	
	
	/** 메시지를 형식화 합니다. 여기서는 앞에 공백을 삽입합니다. */
	private static void  formatPrint(String msg) {
		logger.info("    " + msg);
	}
	
	/** 웹요청 정보를 Console에 출력합니다.  개발시에만 사용하도록 합니다. */ 
	@SuppressWarnings("rawtypes")
	public synchronized  static void printRequestInfo(HttpServletRequest req) {
		
		// for debug  by Andy
		formatPrint("\n");
		logger.info("--------------------------------------------------");
		logger.info("--------------------------------------------------");
		logger.info("Web application root=" + req.getSession().getServletContext().getRealPath("/"));
		logger.info("1.HTTPREQUEST INFORMATION.");
		formatPrint("URI:" + req.getRequestURI());
		formatPrint("QueryString:" + req.getQueryString());
		formatPrint("Form Parameters==:");
		
		Map pmap = req.getParameterMap();
		Iterator iterator = pmap.keySet().iterator(); 
 
		while(iterator.hasNext()) { 
			String key = (String)iterator.next();
			String val = req.getParameter(key);
			String[] paramValues = req.getParameterValues(key);
			if(key.indexOf("data") > -1) continue; 
			if(paramValues != null) {
				if(paramValues.length > 1) { 
					formatPrint("      " + key + "= { " );
					for (int i = 0; i < paramValues.length; i++) {
						formatPrint("          [" + i + "]" +  paramValues[i] );
					}
					formatPrint("      }");
				}
				else{ 
					formatPrint("      " + key + "=" + val);
				}
			}
			else { 
				formatPrint("      " + key + "=" + val);
			}
		}
		pmap = null;  // collect garbage
		logger.info("2. HEADER INFO.");
		Enumeration enum1 = req.getHeaderNames();
		while(enum1.hasMoreElements()) {
			String headerName = (String)enum1.nextElement();
			String str =  req.getHeader(headerName); 
			formatPrint("[" + headerName + "]" + str);
		}
		logger.info("--------------------------------------------------");
		logger.info("--------------------------------------------------");		
	}
}