package com.hmart.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hmart.common.user.SessionUtil;
import com.hmart.common.user.UserDto;

public class SessionInterceptor extends HandlerInterceptorAdapter {
	Logger log = Logger.getLogger(SessionInterceptor.class.getName());
	
	@Override
	/* 클라이언트의 요청을 컨트롤러에 전달하기 전에 호출 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("SessionInterceptor : preHandle");
		
		UserDto user = (UserDto)SessionUtil.getSession(request);
		if(user!=null){
			log.info("user.getSabun()" + user.getSabun());	
		}
//		if (checkEvent(request)) {
//			displayEventExpirationPage(request, response);
//			return false;
//		}
		return true;
	}

	/* 컨트롤러가 요청을 처리한 뒤에 호출 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("SessionInterceptor : postHandle");
		super.postHandle(request, response, handler, modelAndView);
	}

	/* 클라이언트 요청을 처리한 뒤, 즉, 뷰를 통해서 클라이언트에 응답을 전송한 뒤에 실행 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("SessionInterceptor : afterCompletion");
		super.afterCompletion(request, response, handler, ex);
	}
	
//	private boolean checkEvent(HttpServletRequest request) {
//		return request.getRequestURI().equals(request.getContextPath() + "/event/list.do");
//	}

//	private void displayEventExpirationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.sendRedirect("eventExpired.do");
//	}

}
