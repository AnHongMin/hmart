package com.hmart.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 
 * @author 안홍민
 *
 */

public class DispatchAction extends MultiActionController{
	/**
	 * Ajax Response 위한 함수.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param msg Object
	 * @param charset String
	 * @param contentType String
	 */
	public void ajaxResponse(HttpServletRequest request, HttpServletResponse response, Object msg, String charset, String contentType) {
		PrintWriter out = null;
		try {
			response.setContentType(contentType);
			request.setCharacterEncoding(charset);
			response.setHeader("Cache-Control", "no-cache");

			out = response.getWriter();
		} catch (UnsupportedEncodingException e1) {
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		out.print(msg);
		out.flush();
		out.close();
	}

	/**
	 * Ajax Html response.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param msg Object
	 */
	public void ajaxResponseHtml(HttpServletRequest request, HttpServletResponse response, Object msg) {
		ajaxResponse(request, response, msg, "utf-8", "text/html;charset=utf-8");
	}

	/**
	 * Ajax Xml response.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param msg Object
	 */
	public void ajaxResponseXml(HttpServletRequest request, HttpServletResponse response, Object msg) {
		ajaxResponse(request, response, msg, "utf-8", "text/xml;charset=utf-8");
	}
	
	/**
	 * Ajax Json response.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param msg Object
	 */
	public void ajaxResponseJson(HttpServletRequest request, HttpServletResponse response, Object msg) {
		ajaxResponse(request, response, msg, "UTF-8", "application/x-json;charset=UTF-8");
	}

	
	protected ApplicationContext context;
	public DispatchAction(){
		String[] config = new String[]{"com/hmart/config/applicationContext.xml"};
		context = new ClassPathXmlApplicationContext(config);
	}
}
