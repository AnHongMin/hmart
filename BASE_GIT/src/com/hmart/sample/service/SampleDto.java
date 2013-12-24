package com.hmart.sample.service;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.hmart.common.util.Paging;

public class SampleDto extends Paging{
	
	private String method;
	private String seq;
	private String sabun;
	private String name;
	private String content;
	private ArrayList<MultipartFile> attach = new ArrayList<MultipartFile>();

	private String sidx;			// jqGrid 정렬 컬럼
	private String sord;			// jqGrid 정렬 오름차순(asc) / 내림차순(desc)
	private String _search;			// jqGrid 검색 플래그
	private String searchField;		// jqGrid 검색 필드
	private String searchString;	// jqGrid 검색어
	private String searchOper;		// jqGrid 커맨드
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSabun() {
		return sabun;
	}
	public void setSabun(String sabun) {
		this.sabun = sabun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<MultipartFile> getAttach() {
		return attach;
	}
	public void setAttach(ArrayList<MultipartFile> attach) {
		this.attach = attach;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String get_search() {
		return _search;
	}
	public void set_search(String _search) {
		this._search = _search;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	@SuppressWarnings("deprecation")
	public String getSearchString() {
		return URLDecoder.decode(searchString);
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
}
