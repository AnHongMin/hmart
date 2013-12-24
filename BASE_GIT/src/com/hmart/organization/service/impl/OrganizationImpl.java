package com.hmart.organization.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import com.hmart.common.util.json.JSONArray;
import com.hmart.common.util.json.JSONObject;
import com.hmart.organization.service.OrganizationService;
import com.hmart.organization.service.DeptDto;
import com.hmart.organization.service.MemberDto;
import com.hmart.organization.service.OrganizationVo;

public class OrganizationImpl implements OrganizationService{
	@Resource(name="organizationDao")
	private OrganizationDao organizationDao;
	
	public ArrayList<DeptDto> getDeptList(OrganizationVo vo) throws Exception{
		return organizationDao.getDeptList(vo);
	}
	
	public String makeJSONDeptAllNode(OrganizationVo vo) throws Exception {
		ArrayList<DeptDto> list = this.getDeptList(vo);
		HashMap<String, JSONObject> h = new HashMap<String, JSONObject>();
		// JSON Array List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
//			if(Integer.parseInt(String.valueOf(dept.getCount()))!=0){
//				node.put("isFolder", true);	
//			}
			node.put("icon", "icons/folder.gif");
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

	@SuppressWarnings("deprecation")
	public String makeJSONDeptSearchNode(OrganizationVo vo) throws Exception {
		if(vo.getSearchString()!=null){
			String searchString = URLDecoder.decode(vo.getSearchString());
			vo.setSearchString(searchString);
		}
		ArrayList<DeptDto> list = this.getDeptList(vo);
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			node.put("icon", "icons/folder.gif");
			json.put(node);
		}
		return json.toString();
	}
	
	public ArrayList<MemberDto> getMemberList(OrganizationVo vo) throws Exception{
		return organizationDao.getMemberList(vo);
	}
	
	public ArrayList<DeptDto> getDeptMemberList(OrganizationVo vo) throws Exception{
		return organizationDao.getDeptMemberList(vo);
	}
	
	public String makeJSONDeptMemberAllNode(OrganizationVo vo) throws Exception {
		ArrayList<DeptDto> list = this.getDeptMemberList(vo);
		HashMap<String, JSONObject> h = new HashMap<String, JSONObject>();
		// JSON Array List 생성.
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			if("B".equals(dept.getOrg_code())){
				node.put("hideCheckbox", true);	
			}
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
	
	@SuppressWarnings("deprecation")
	public String makeJSONMemberSearchNode(OrganizationVo vo) throws Exception {
		if(vo.getSearchString()!=null){
			String searchString = URLDecoder.decode(vo.getSearchString());
			vo.setSearchString(searchString);
		}
		ArrayList<MemberDto> list = this.getMemberList(vo);
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			MemberDto member = (MemberDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", member.getOrg_code());
			node.put("title", member.getName());
			node.put("icon", "icons/user.gif");
			json.put(node);
		}
		return json.toString();
	}
	
	@SuppressWarnings("deprecation")
	public String makeJSONDeptMemberSearchNode(OrganizationVo vo) throws Exception {
		if(vo.getSearchString()!=null){
			String searchString = URLDecoder.decode(vo.getSearchString());
			vo.setSearchString(searchString);
		}
		ArrayList<DeptDto> list = this.getDeptMemberList(vo);
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			DeptDto dept = (DeptDto) list.get(i);
			JSONObject node = new JSONObject();
			node.put("key", dept.getOrg_code());
			node.put("title", dept.getOrg_name());
			if(Integer.parseInt(String.valueOf(dept.getCount()))!=0){
				node.put("icon", "icons/folder.gif");
			}else{
				node.put("icon", "icons/user.gif");
			}
			json.put(node);
		}
		return json.toString();
	}
}
