package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hmart.sample.service.StateDto;
import com.ibatis.sqlmap.client.SqlMapClient;

@Component
public class StateDao{
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMap;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StateDto> getStateList() throws SQLException {
		return (ArrayList<StateDto>)sqlMap.queryForList("State.getStateList");	
	}
}
