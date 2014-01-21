package com.hmart.sample.service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SampleService {
	public ArrayList<SampleDto> getStateList() throws SQLException;
}
