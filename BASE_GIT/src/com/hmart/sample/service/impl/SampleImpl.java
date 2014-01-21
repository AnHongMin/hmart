package com.hmart.sample.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.hmart.common.util.json.JSONArray;
import com.hmart.common.util.json.JSONObject;
import com.hmart.sample.service.DeptDto;
import com.hmart.sample.service.FileService;
import com.hmart.sample.service.MemberDto;
import com.hmart.sample.service.SampleDto;
import com.hmart.sample.service.SampleService;
import com.hmart.sample.service.StateDto;

public class SampleImpl implements SampleService{
	@Resource(name="sampleDao")
	private SampleDao sampleDao;
	@Resource(name="fileImpl")
	private FileService fileImpl;
	
	public ArrayList<StateDto> getStateList() throws SQLException {
		return sampleDao.getStateList();
	}
	
	public int getSampleListCount(SampleDto dto) throws Exception{
		return sampleDao.getSampleListCount(dto);
	}

	public ArrayList<SampleDto> getSampleList(SampleDto dto) throws Exception{
		int cnt = sampleDao.getSampleListCount(dto);
		if(cnt==0) return null;
		dto.setTotalCount(cnt);
		dto.checkPageNo();
		return sampleDao.getSampleList(dto);
	}

	public SampleDto getSample(SampleDto dto) throws Exception {
		return sampleDao.getSample(dto);
	}
	
	/* 428 Page */
	@Transactional(rollbackFor=Exception.class)
	public void insertSample(SampleDto dto) throws Exception{
		sampleDao.insertSample(dto);
		if(dto.getAttach()!=null)
			fileImpl.saveFileListInfo(dto.getSeq(), "sample", dto.getAttach());
	}
	
	public void updateSample(SampleDto dto) throws SQLException {
		sampleDao.updateSample(dto);
	}
	
	public long getNewSeq() throws SQLException{
		return sampleDao.getNewSeq();
	}
	
	public void deleteSample(String seq) throws SQLException{
		sampleDao.deleteSample(seq);
	}
	
	public ArrayList<DeptDto> getDeptList(DeptDto dto) throws Exception{
		return sampleDao.getDeptList(dto);
	}
	
	public String makeJSONDeptAllNode(DeptDto dto) throws Exception {
		ArrayList<DeptDto> list = this.getDeptList(dto);

		HashMap<String, JSONObject> h = new HashMap<String, JSONObject>();

		// JSON Array List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			if(Integer.parseInt(String.valueOf(dept.getCount()))!=0){
				node.put("isFolder", true);	
			}
			h.put(dept.getOrg_code(), node);
		}

		// JSON Tree List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject parentNode = new JSONObject();
			JSONObject childNode = new JSONObject();

			parentNode = (JSONObject) h.get(dept.getOrg_upper_cd());
			childNode = (JSONObject) h.get(dept.getOrg_code());
			if (!("B".equals(dept.getOrg_code())) && parentNode != null) {
				parentNode.append("children", childNode);
				h.put(dept.getOrg_upper_cd(), parentNode);
			}

		}

		JSONObject rootNode = new JSONObject();
		rootNode = (JSONObject) h.get("B");

		return rootNode.toString();
	}

	public String makeJSONDeptSearchNode(DeptDto dto) throws Exception {
		ArrayList<DeptDto> list = this.getDeptList(dto);
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			json.put(node);
		}
		return json.toString();
	}

	
	public ArrayList<MemberDto> getMemberList(MemberDto dto) throws Exception{
		return sampleDao.getMemberList(dto);
	}
	
	public ArrayList<DeptDto> getDeptMemberList(DeptDto dto) throws Exception{
		return sampleDao.getDeptMemberList(dto);
	}
	
	public String makeJSONDeptMemberAllNode(DeptDto dto) throws Exception {
		ArrayList<DeptDto> list = this.getDeptMemberList(dto);

		HashMap<String, JSONObject> h = new HashMap<String, JSONObject>();

		// JSON Array List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			if(Integer.parseInt(String.valueOf(dept.getCount()))!=0){
				node.put("isFolder", true);
			}else{
				node.put("icon", "icons/user.gif");
			}
			h.put(dept.getOrg_code(), node);
		}

		// JSON Tree List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject parentNode = new JSONObject();
			JSONObject childNode = new JSONObject();

			parentNode = (JSONObject) h.get(dept.getOrg_upper_cd());
			childNode = (JSONObject) h.get(dept.getOrg_code());
			if (!("B".equals(dept.getOrg_code())) && parentNode != null) {
				parentNode.append("children", childNode);
				h.put(dept.getOrg_upper_cd(), parentNode);
			}

		}

		JSONObject rootNode = new JSONObject();
		rootNode = (JSONObject) h.get("B");

		return rootNode.toString();
	}

}
