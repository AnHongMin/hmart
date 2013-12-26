package com.hmart.common.util;

/**
 * 
 * 페이징 처리 클래스
 * 
 * @author hongmin.an
 *
 */
public class Paging{

	/** Constant DEFAULT_LIST_BLOCK. */
	public static final int DEFAULT_LIST_BLOCK = 15;

	/** Constant DEFAULT_PAGE_BLOCK. */
	public static final int DEFAULT_PAGE_BLOCK = 10;

	/** Constant AVAILABLE_LIST_BLOCK. */
	public static final int AVAILABLE_LIST_BLOCK = 300;

	/** startNum. */
	private int startNum;

	/** endNum. */
	private int endNum;

	/** pageNo. */
	private int pageNo;

	/** listBlock. */
	private int listBlock;

	/** pageBlock. */
	private int pageBlock;

	/** totalCount. */
	private int totalCount;

	/** temp no. */
	private int tempNo;

	/**
	 * 생성자.
	 */
	public Paging() {
		super();
		pageNo = 1;
		listBlock = DEFAULT_LIST_BLOCK;
		pageBlock = DEFAULT_PAGE_BLOCK;
		tempNo = -1;
	}

	public int getTotalPageCnt() {
		int i = totalCount / listBlock;
		if (totalCount % listBlock > 0)
			i++;
		return i;
	}

	public int getListNo() {
		if (tempNo == -1)
			tempNo = getStartNum();
		return tempNo++;
	}

	public int getStartNum() {
		startNum = (pageNo * listBlock) - listBlock + 1;
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getOffsetNum() {
		return (pageNo * listBlock) - listBlock;
	}

	public int getEndNum() {
		endNum = (pageNo * listBlock > totalCount) ? totalCount : (pageNo * listBlock);
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getListBlock() {
		return listBlock;
	}

	public void setListBlock(int listBlock) {
		if (listBlock > AVAILABLE_LIST_BLOCK || listBlock < 1)
			return;

		this.listBlock = listBlock;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTempNo() {
		return tempNo;
	}

	public void setTempNo(int tempNo) {
		this.tempNo = tempNo;
	}

	public void checkPageNo(){
		if(pageNo <0){
			pageNo = 1;
		}else if(pageNo * listBlock > totalCount){
			pageNo = getTotalPageCnt();
		}
	}

	public int getListRelativeStartNum(){
		return totalCount - (pageNo-1)*listBlock;
	}

	public int getStartNumMysql() {
		startNum = (pageNo * listBlock) - listBlock + 1;
		return startNum-1;
	}
}
