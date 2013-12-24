package com.hmart.organization.service;

public class DeptDto{
	
	private String org_code;		// 조직코드
	private String org_name;		// 조직명
	private String count;			// 부서 하위 갯수
	private String org_upper_cd;	// 조직도 상위 코드
	
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getOrg_upper_cd() {
		return org_upper_cd;
	}
	public void setOrg_upper_cd(String org_upper_cd) {
		this.org_upper_cd = org_upper_cd;
	}
}
