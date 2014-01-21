package com.hmart.sample.service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StateService {
	public ArrayList<StateDto> getStateList() throws SQLException;
}
