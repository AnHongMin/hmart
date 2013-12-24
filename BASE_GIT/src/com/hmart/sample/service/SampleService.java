package com.hmart.sample.service;

import java.util.ArrayList;

/**
 * 
 * 샘플
 * 
 * @author 안홍민
 *
 */
public interface SampleService {
	
	/**
	 * 목록 카운트
	 * @param dto SampleDto
	 * @return 
	 * @throws Exception
	 * @author 안홍민
	 */
	public int getSampleListCount(SampleDto dto) throws Exception;
	
	/**
	 * 목록
	 * @param dto SampleDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<SampleDto> getSampleList(SampleDto dto) throws Exception;
	
	/**
	 * 조회
	 * @param dto SampleDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public SampleDto getSample(SampleDto dto) throws Exception;
	
	/**
	 * 등록
	 * @param dto SampleDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public void insertSample(SampleDto dto) throws Exception;
	
	/**
	 * 수정
	 * @param dto SampleDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public void updateSample(SampleDto dto) throws Exception;
	
	/**
	 * 고유키
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public long getNewSeq() throws Exception;
	
	/**
	 * 삭제
	 * @param seq String
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public void deleteSample(String seq) throws Exception;
	
	/**
	 * 조직도 부서 목록
	 * @param dto DeptDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<DeptDto> getDeptList(DeptDto dto) throws Exception;

	/**
	 * JSON 부서트리데이터만들기(모든 노드)
	 * @param dto DeptDto
	 * @return
	 * @throws Exception
	 * @author 안홍민 
	 */
	public String makeJSONDeptAllNode(DeptDto dto) throws Exception;
	
	/**
	 * JSON 부서검색 트리데이터만들기
	 * @param dto DeptDto
	 * @return
	 * @throws Exception
	 * @author 안홍민  
	 */
	public String makeJSONDeptSearchNode(DeptDto dto) throws Exception;
	
	/**
	 * 조직도 사용자 목록
	 * @param dto MemberDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<MemberDto> getMemberList(MemberDto dto) throws Exception;
	
	/**
	 * 조직도 부서+사용자 목록
	 * @param dto DeptDto
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<DeptDto> getDeptMemberList(DeptDto dto) throws Exception;
	
	/**
	 * JSON 부서+사용자트리데이터만들기(모든 노드)
	 * @param dto DeptDto
	 * @return
	 * @throws Exception
	 * @author 안홍민 
	 */
	public String makeJSONDeptMemberAllNode(DeptDto dto) throws Exception;
}
