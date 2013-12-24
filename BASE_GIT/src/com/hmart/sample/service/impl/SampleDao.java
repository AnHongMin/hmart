package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.hmart.sample.service.DeptDto;
import com.hmart.sample.service.MemberDto;
import com.hmart.sample.service.SampleDto;

public class SampleDao{
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMap;
	
	/**
	 * 목록 카운트
	 * @param dto SampleDto
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public int getSampleListCount(SampleDto dto) throws SQLException {
		return ( (Integer) sqlMap.queryForObject("Sample.getSampleListCount",  dto) ).intValue();
	}

	/**
	 * 목록
	 * @param dto SampleDto
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SampleDto> getSampleList(SampleDto dto) throws SQLException {
		return (ArrayList<SampleDto>)sqlMap.queryForList("Sample.getSampleList",dto);	
	}
	
	/**
	 * 조회
	 * @param dto SampleDto
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public SampleDto getSample(SampleDto dto) throws SQLException {
		return (SampleDto)sqlMap.queryForObject("Sample.getSample", dto);
	}
	
	/**
	 * 등록
	 * @param dto SampleDto
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public void insertSample(SampleDto dto) throws SQLException {
		sqlMap.insert("Sample.insertSample", dto);
	}
	
	/**
	 * 수정
	 * @param dto SampleDto
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public void updateSample(SampleDto dto) throws SQLException {
		sqlMap.update("Sample.updateSample", dto);
	}
	
	/**
	 * seq
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public long getNewSeq() throws SQLException {
		return ((Long) sqlMap.queryForObject("Sample.getNewSeq", null)).longValue();
	}
	
	/**
	 * 삭제
	 * @param seq String
	 * @return
	 * @throws SQLException 
	 * @author 안홍민
	 */
	public void deleteSample(String seq) throws SQLException {
		sqlMap.delete("Sample.deleteSample", seq);
	}
	
	/**
	 * 조직도 부서 목록
	 * @param dto DeptDto
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<DeptDto> getDeptList(DeptDto dto) throws SQLException {
		return (ArrayList<DeptDto>)sqlMap.queryForList("Sample.getDeptList",dto);	
	}
	
	/**
	 * 조직도 사용자 목록
	 * @param dto MemberDto
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<MemberDto> getMemberList(MemberDto dto) throws SQLException {
		return (ArrayList<MemberDto>)sqlMap.queryForList("Sample.getMemberList",dto);	
	}
	
	/**
	 * 조직도 부서+사용자 목록
	 * @param dto DeptDto
	 * @return
	 * @throws SQLException
	 * @author 안홍민
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<DeptDto> getDeptMemberList(DeptDto dto) throws SQLException {
		return (ArrayList<DeptDto>)sqlMap.queryForList("Sample.getDeptMemberList",dto);	
	}
}
