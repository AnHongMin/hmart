package com.hmart.organization.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hmart.common.util.DispatchAction;
import com.hmart.common.util.json.JSONUtil;
import com.hmart.organization.service.OrganizationService;
import com.hmart.organization.service.OrganizationVo;

/**
 * 
 * 조직도 컨트롤러
 * 
 * @author 안홍민
 *
 */

@Controller
public class OrganizationController extends DispatchAction{
	/** logger. */
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(this.getClass());
	
	OrganizationService organizationImpl;
	
	public void setOrganizationImpl(OrganizationService organizationImpl) {
		this.organizationImpl = organizationImpl;
	}

	/**
	 * Tree
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param vo OrganizationVo
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tree(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("vo") OrganizationVo vo) throws Exception{
		/* 셀렉트 박스 옵션 처리 */
		String select ="";
		if("1".equals(vo.getSelectMode())){
			select ="checkbox: true, classNames: {checkbox: \"dynatree-radio\"}, selectMode: "+vo.getSelectMode()+",";
		}else if("2".equals(vo.getSelectMode()) || "3".equals(vo.getSelectMode())){
			select ="checkbox: true, selectMode: "+vo.getSelectMode()+",";	
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("organization/tree");
		mav.addObject("vo", vo);
		mav.addObject("select", select);
		//mav.addObject("result", organizationImpl.makeJSONDeptAllNode(vo));
		return mav;
	}

	/**
	 * TreeAjax
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param vo OrganizationVo 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView treeAjax(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") OrganizationVo vo) throws Exception{
		String result = "";
		if(vo.getType()!=null){
			// 부서
			if("1".equals(vo.getType())){
				// 전체  노드
				if(vo.getSearchString()==null || "".equals(vo.getSearchString())){
					result = organizationImpl.makeJSONDeptAllNode(vo);
				// 검색된 부서 노드
				}else{
					result = organizationImpl.makeJSONDeptSearchNode(vo);	
				}
			// 부서+사용자
			}else if("2".equals(vo.getType())){
				// 전체 노드
				if(vo.getSearchString()==null || "".equals(vo.getSearchString())){
					result = organizationImpl.makeJSONDeptMemberAllNode(vo);
				}else{
					// 검색된 부서 노드
					if("org_name".equals(vo.getSearchField())){
						result = organizationImpl.makeJSONDeptSearchNode(vo);	
					// 검색된 사용자 노드
					}else if("name".equals(vo.getSearchField())){
						result = organizationImpl.makeJSONMemberSearchNode(vo);
					// 검색된 부서+사용자 노드						
					}else if("A.org_name".equals(vo.getSearchField())){
						result = organizationImpl.makeJSONDeptMemberSearchNode(vo);
					}
				}
			}
		}
		ajaxResponseJson(req, res, JSONUtil.toJSON(result));
		return null;
	}
}
