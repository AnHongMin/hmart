package com.hmart.sample.service;

import java.sql.SQLException;
import java.util.List;

public interface SampleService {
	public List<Object> getStateList() throws SQLException;
}
