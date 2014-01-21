package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hmart.sample.service.SampleDto;
import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class SampleDao{
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMap;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SampleDto> getStateList() throws SQLException {
		return (ArrayList<SampleDto>)sqlMap.queryForList("Sample.getStateList");	
	}
}
