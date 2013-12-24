package com.hmart.common.user;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 이 클래스에는 세션에 담겨 있는 정보를 핸들링 하기위한 함수들이 정의 되어 있다.
 *
 * @author 안홍민
 * 
 */

@SuppressWarnings("serial")
public class SessionUtil  implements Serializable {
	
	/** 사용자 정보 세션 명 */
	public static final String SESSION_NAME = "SAMPLE";

	/**
	 * 사용자 정보를 파라미터로 받아 세션 명과 같이 Login 함수에 넘겨 준다. 
	 *
	 * @param request HttpServletRequest
	 * @param user 로그인한 사용자 정보가 담긴 bean 객체
	 */
	public static void Login(HttpServletRequest request, UserDto user) {
		Login(request, user, SESSION_NAME);
	}

	/**
	 * 파라미터로 사용자 정보와 세션 명을 받아 HttpServletRequest에 세션을 등록하는 함수이다.
	 *
	 * @param request HttpServletRequest
	 * @param user 사용자 정보가 담긴 bean 객체
	 * @param sessionName 세션 명
	 */
	public static void Login(HttpServletRequest request, UserDto user, String sessionName) {
		HttpSession session = request.getSession();
		session.removeAttribute(sessionName);
		
		if(user != null && user.getExpire() != 0 ){
			session.setMaxInactiveInterval(user.getExpire() * 60);
		} else {
			session.setMaxInactiveInterval(7200);
		}
		session.setAttribute(sessionName, user);
	}

	/**
	 * 세션에 등록 되어 있는 사용자 정보를 삭제 한다.
	 *
	 * @param request HttpServletRequest
	 */
	public static void Logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
	}

	/**
	 * 세션에서 사용자 정보를 구해서 리턴 해준다.
	 *
	 * @param request HttpServletRequest
	 * @return 사용자 정보가 있을 경우 정보가 담긴 bean 객체를 리턴하고 없을 경우에는 null을 리턴 한다.
	 */
	public static UserDto getSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		UserDto user = null;
		if (session != null) {
			user = (UserDto) session.getAttribute(SESSION_NAME);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 세션에서 사용자 정보를 구해서 리턴 해준다.
	 *
	 * @param request HttpServletRequest
	 * @param sessionName 세션 명
	 * @return 사용자 정보가 있을 경우 정보가 담긴 bean 객체를 리턴하고 없을 경우에는 null을 리턴 한다.
	 */
	public static UserDto getSession(HttpServletRequest request, String sessionName) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			UserDto user = (UserDto) session.getAttribute(sessionName);
			return user;
		}else{
			return null;
		}
	}

	/**
	 * 현재 아이디와 로그인 한 아이디가 같은지 확인 한다.
	 *
	 * @param request HttpServletRequest
	 * @param empId 사원번호
	 * @return 아이디가 같으면 true 같지 않으면 false
	 */
	public static boolean existSession(HttpServletRequest request,String sabun) {
		HttpSession session = request.getSession(false);
		boolean ret = false;
		if (session != null) {
			UserDto user = (UserDto) session.getAttribute(SESSION_NAME);
			if (sabun.equals(user.getSabun())) {
				ret = true;
			} else {
				/**
				 * 현재 아이디와 로그인된 아이다가 다르면 로그인 안된걸로 처리
				 */
				ret = false;
			}
		} else {
			ret = false;
		}

		return ret;
	}
}
