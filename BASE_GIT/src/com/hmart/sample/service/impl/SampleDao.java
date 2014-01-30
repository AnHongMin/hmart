package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.hmart.sample.service.ApplicationmenumstDto;
import com.hmart.sample.service.SampleVo;

@Component
public class SampleDao{
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;   

	public List<Object> getStateList() throws SQLException {
		return sqlSession.selectList("Sample.getStateList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ApplicationmenumstDto> getApplicationmenumstList(SampleVo vo) throws SQLException {
		return (List)sqlSession.selectList("Sample.getApplicationmenumstList", vo);
	}
}
