package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hmart.sample.service.SampleDto;
import com.hmart.sample.service.SampleService;

@Component
public class SampleImpl implements SampleService{
	@Resource(name="sampleDao")
	private SampleDao sampleDao;
	
	public ArrayList<SampleDto> getStateList() throws SQLException {
		return sampleDao.getStateList();
	}
}
