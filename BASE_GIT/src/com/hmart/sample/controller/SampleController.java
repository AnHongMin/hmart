package com.hmart.sample.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hmart.common.util.DispatchAction;
import com.hmart.common.util.RequestPrint;
import com.hmart.common.util.json.JSONArray;
import com.hmart.common.util.json.JSONObject;
import com.hmart.common.util.json.JSONUtil;
import com.hmart.sample.service.SampleDto;
import com.hmart.sample.service.SampleService;

/**
 * 
 * 샘플 컨트롤러
 * 
 * @author hongmin.an
 *
 */
@Controller
public class SampleController extends DispatchAction{
//	private Log logger = LogFactory.getLog(this.getClass());
	
//	SampleService sampleService = (SampleService) context.getBean("sampleImpl");
	
	@Resource(name="sampleImpl")
	SampleService sampleImpl;
	
	/**
	 * test
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample.do",params="method=test")
	public ModelAndView test(HttpServletRequest req, HttpServletResponse res) throws Exception{
		ModelAndView mav = new ModelAndView();
		ArrayList<SampleDto> list = sampleImpl.getStateList();
		mav.addObject("list",list);
		mav.setViewName("sample/test");
		return mav;
	}
	
	/**
	 * testData
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample.do",params="method=testData")
	public void testData(HttpServletRequest req, HttpServletResponse res) throws Exception{
		RequestPrint.printRequestInfo(req);
		JSONArray data = new JSONArray();
		int totalCount = 29;
		for (int i = 1; i < totalCount; i++) {
			JSONObject node = new JSONObject();
			node.put("operator",i );
			node.put("transaction", i);
			node.put("ringtime", "ringtime"+i);
			node.put("net", "net"+i);
			node.put("item", "item"+i);
			data.put(node);
		}
		
		JSONObject node = new JSONObject();
		node.put("totalCount", totalCount);
		node.put("dataset", data);
		ajaxResponseJson(req, res, JSONUtil.toJSON(node));
	}
	
	/**
	 * branchData
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample.do",params="method=branchData")
	public void branchData(HttpServletRequest req, HttpServletResponse res) throws Exception{
		RequestPrint.printRequestInfo(req);
		String stateid = req.getParameter("stateid");
		int istateid = Integer.parseInt(stateid);
		
		JSONArray data = new JSONArray();
		int totalCount = 5;
		for (int i = 1; i < totalCount; i++) {
			JSONObject node = new JSONObject();
			node.put("branchid",istateid*i );
			node.put("branchname", "branch"+(istateid*i));
			data.put(node);
		}
		
		JSONObject node = new JSONObject();
		node.put("totalCount", totalCount);
		node.put("data", data);
		ajaxResponseJson(req, res, JSONUtil.toJSON(node));
	}
	
	
	/**
	 * chartData
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sample.do",params="method=chartData")
	public void chartData(HttpServletRequest req, HttpServletResponse res) throws Exception{
		RequestPrint.printRequestInfo(req);
		JSONArray data = new JSONArray();
		String HOUR[] = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"};
		String WORKINGMIN[] = {"4.07","14.45","22.02","24.07","30.07","31.45","31.03","29.52","37.77","37.93","39.95","20.70","12.95","1.47"};
		for (int i = 0; i < HOUR.length; i++) {
			JSONObject node = new JSONObject();
			node.put("BRANCHID","18");
			node.put("HOUR", HOUR[i]);
			node.put("WORKINGMIN", WORKINGMIN[i]);
			data.put(node);
		}
		
		JSONObject node = new JSONObject();
		node.put("data", data);
		ajaxResponseJson(req, res, JSONUtil.toJSON(node));
	}
}
