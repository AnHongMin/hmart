package com.hmart.organization.service;

public class OrganizationVo{
	
	private String searchField;		// 검색 필드
	private String searchString;	// 검색어
	
	private String selectMode;		// 노드에 셀렉트 박스 추가여부
	private String type;			// 트리 구성	ex) 1:부서, 2:부서+사용자
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
