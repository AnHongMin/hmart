package com.hmart.organization.service;

import java.util.ArrayList;

/**
 * 
 * 조직도
 * 
 * @author 안홍민
 *
 */
public interface OrganizationService {
	/**
	 * 조직도 부서 목록
	 * @param vo OrganizationVo
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<DeptDto> getDeptList(OrganizationVo vo) throws Exception;

	/**
	 * JSON 부서트리데이터만들기(모든 노드)
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 * @author 안홍민 
	 */
	public String makeJSONDeptAllNode(OrganizationVo vo) throws Exception;
	
	/**
	 * JSON 부서검색 트리데이터만들기
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 * @author 안홍민  
	 */
	public String makeJSONDeptSearchNode(OrganizationVo vo) throws Exception;
	
	/**
	 * 조직도 사용자 목록
	 * @param vo OrganizationVo
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<MemberDto> getMemberList(OrganizationVo vo) throws Exception;

	/**
	 * 조직도 부서+사용자 목록
	 * @param vo OrganizationVo
	 * @return  
	 * @throws Exception
	 * @author 안홍민
	 */
	public ArrayList<DeptDto> getDeptMemberList(OrganizationVo vo) throws Exception;
	
	/**
	 * JSON 부서+사용자트리데이터만들기(모든 노드)
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 * @author 안홍민 
	 */
	public String makeJSONDeptMemberAllNode(OrganizationVo vo) throws Exception;
	
	/**
	 * JSON 사용자검색 트리데이터만들기
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 * @author 안홍민  
	 */
	public String makeJSONMemberSearchNode(OrganizationVo vo) throws Exception;
	
	/**
	 * JSON 부서+사용자검색 트리데이터만들기
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 * @author 안홍민  
	 */
	public String makeJSONDeptMemberSearchNode(OrganizationVo vo) throws Exception;
}
