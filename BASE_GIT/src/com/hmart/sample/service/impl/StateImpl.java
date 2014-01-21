package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hmart.sample.service.StateDto;
import com.hmart.sample.service.StateService;

@Component
public class StateImpl implements StateService{
	@Resource(name="stateDao")
	private StateDao stateDao;
	
	public ArrayList<StateDto> getStateList() throws SQLException {
		return stateDao.getStateList();
	}
}
