package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hmart.common.util.json.JSONObject;
import com.hmart.sample.service.ApplicationmenumstDto;
import com.hmart.sample.service.SampleService;
import com.hmart.sample.service.SampleVo;

@Component
public class SampleImpl implements SampleService{
	@Resource(name="sampleDao")
	private SampleDao sampleDao;
	
	public List<Object> getStateList() throws SQLException {
		return sampleDao.getStateList();
	}
	
	public String getApplicationmenumstList(SampleVo vo) throws Exception {
		List<ApplicationmenumstDto> list = sampleDao.getApplicationmenumstList(vo);
		HashMap<String, JSONObject> h = new HashMap<String, JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			ApplicationmenumstDto dto = (ApplicationmenumstDto)list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dto.getMenuId());
			node.put("text", dto.getMumyscrt());
			node.put("leaf", "true");
			h.put(dto.getMenuId(), node);
		}
		
		for (int i = 0; i < list.size(); i++) {
			ApplicationmenumstDto dto = (ApplicationmenumstDto)list.get(i);
			JSONObject parentNode = new JSONObject();
			JSONObject childNode = new JSONObject();
			parentNode = (JSONObject) h.get(dto.getUpmenuId());
			childNode = (JSONObject) h.get(dto.getMenuId());
			if (!((vo.getRoot()+"0").equals(dto.getMenuId())) && parentNode != null) {
				parentNode.put("expanded", "true");
				parentNode.remove("leaf");	
				parentNode.append("children", childNode);
				h.put(dto.getUpmenuId(), parentNode);
			}
		}
		JSONObject rootNode = new JSONObject();
		rootNode = (JSONObject) h.get((vo.getRoot()+"0"));
		return rootNode.toString();
	}
}
