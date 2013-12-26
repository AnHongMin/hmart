package com.hmart.common.user;

import java.io.Serializable;

/**
 * 사용자 정보를 담는 자바 bean 클래스
 * 
 * @author hongmin.an
 *
 */
@SuppressWarnings("serial")
public class UserDto implements Serializable,Cloneable {

	/** 세션 종료 시간. */
	private int expire;

	/** 사용자 코드 */
	private String sabun;

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getSabun() {
		return sabun;
	}

	public void setSabun(String sabun) {
		this.sabun = sabun;
	}
}