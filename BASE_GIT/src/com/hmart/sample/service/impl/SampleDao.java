package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class SampleDao{
	
	@Resource(name="sqlSession")
	private SqlSession sqlSession;   

	public List<Object> getStateList() throws SQLException {
		return sqlSession.selectList("Sample.getStateList");
	}
}
