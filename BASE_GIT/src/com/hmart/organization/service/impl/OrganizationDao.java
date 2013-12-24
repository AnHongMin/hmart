package com.hmart.organization.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.hmart.organization.service.DeptDto;
import com.hmart.organization.service.MemberDto;
import com.hmart.organization.service.OrganizationVo;

public class OrganizationDao{
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMap;
	
	/**
	 * 조직도 부서 목록
	 * @param vo OrganizationVo
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<DeptDto> getDeptList(OrganizationVo vo) throws SQLException {
		return (ArrayList<DeptDto>)sqlMap.queryForList("Organization.getDeptList",vo);	
	}
	
	/**
	 * 조직도 사용자 목록
	 * @param vo OrganizationVo
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MemberDto> getMemberList(OrganizationVo vo) throws SQLException {
		return (ArrayList<MemberDto>)sqlMap.queryForList("Organization.getMemberList",vo);	
	}
	
	/**
	 * 조직도 부서+사용자 목록
	 * @param vo OrganizationVo
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<DeptDto> getDeptMemberList(OrganizationVo vo) throws SQLException {
		return (ArrayList<DeptDto>)sqlMap.queryForList("Organization.getDeptMemberList",vo);	
	}
}
