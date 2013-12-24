package com.hmart.sample.service;

public class DeptDto{
	
	private String org_code;
	private String org_name;
	private String count;
	private String org_upper_cd;
	
	private String searchField;		// 검색 필드
	private String searchString;	// 검색어
	
	private String selectMode;		// 노드에 셀렉트 박스 추가여부
	
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
	
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSelectMode() {
		return selectMode;
	}
	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}
}
