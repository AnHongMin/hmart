package com.hmart.sample.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.hmart.common.Constants;
import com.hmart.common.util.DispatchAction;
import com.hmart.common.util.RequestPrint;
import com.hmart.common.util.json.JSONException;
import com.hmart.common.util.json.JSONUtil;
import com.hmart.sample.service.DeptDto;
import com.hmart.sample.service.FileDto;
import com.hmart.sample.service.FileService;
import com.hmart.sample.service.MemberDto;
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
	/** logger. */
	private Log logger = LogFactory.getLog(this.getClass());
	
//	SampleService sampleService = (SampleService) context.getBean("sampleImpl");
	
	SampleService sampleImpl;
	FileService fileImpl;
	
	public void setSampleImpl(SampleService sampleImpl) {
		this.sampleImpl = sampleImpl;
	}

	public void setFileImpl(FileService fileImpl) {
		this.fileImpl = fileImpl;
	}
	
	/**
	 * test
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ModelAndView test(HttpServletRequest req, HttpServletResponse res) throws Exception{
		RequestPrint.printRequestInfo(req);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/test");
		return mav;
	}
	
	/**
	 * test
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ModelAndView testData(HttpServletRequest req, HttpServletResponse res) throws Exception{
		RequestPrint.printRequestInfo(req);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/testData");
		return mav;
	}
	
	/**
	 * Tree
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto DeptDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView tree(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") DeptDto dto) throws Exception{
		String select ="";
		if("1".equals(dto.getSelectMode())){
			select ="checkbox: true, classNames: {checkbox: \"dynatree-radio\"}, selectMode: "+dto.getSelectMode()+",";
		}else if("2".equals(dto.getSelectMode()) || "3".equals(dto.getSelectMode())){
			select ="checkbox: true, selectMode: "+dto.getSelectMode()+",";	
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/tree");
		mav.addObject("dto", dto);
		mav.addObject("select", select);
		mav.addObject("result", sampleImpl.makeJSONDeptAllNode(dto));
		return mav;
	}
	
	/**
	 * TreeAjax
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto DeptDto 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView treeAjax(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") DeptDto dto) throws Exception{
		String result = "";
		if(dto.getSearchString()==null || "".equals(dto.getSearchString())){
			//result = sampleImpl.makeJSONDeptAllNode(dto);
			result = sampleImpl.makeJSONDeptMemberAllNode(dto);
		}else{
			result = sampleImpl.makeJSONDeptSearchNode(dto);
		}
		ajaxResponseJson(req, res, JSONUtil.toJSON(result));
		return null;
	}
	
	/**
	 * Grid User
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto MemberDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView gridUser(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") MemberDto dto) throws Exception{
		ModelAndView view =  new ModelAndView("sample/gridUser");
		view.addObject("dto", dto);
		return view;
	}
	
	/**
	 * Grid User 리스트
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto MemberDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView gridUserList(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") MemberDto dto) throws Exception{
		ArrayList<MemberDto> list = sampleImpl.getMemberList(dto);
		HashMap<String, Object> result = new HashMap<String, Object>(); 
		result.put("list", list);
		ajaxResponseJson(req, res, JSONUtil.toJSON(result));
		return null;
	}
	
	/**
	 * Grid
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ModelAndView grid(HttpServletRequest req, HttpServletResponse res) throws Exception{
		ModelAndView view =  new ModelAndView("sample/grid");		
		return view;
	}
	
	/**
	 * Grid 리스트
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView gridList(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws Exception{
		RequestPrint.printRequestInfo(req);
		// 현재 페이지
		String page = req.getParameter("page");
		if(page!=null){
			dto.setPageNo(Integer.parseInt(page));
		}
		// 화면상에 보여줄 줄수 
		String rows = req.getParameter("rows");
		if(rows!=null){
			dto.setListBlock(Integer.parseInt(rows));
		}
		// 검색 플래그
		String _search = req.getParameter("_search");
		if(_search!=null){
			dto.set_search(_search);
		}
		// 검색 필드
		String searchField = req.getParameter("searchField");
		if(searchField!=null){
			dto.setSearchField(searchField);
		}
		// 검색어
		String searchString = req.getParameter("searchString");
		if(searchString!=null){
			dto.setSearchString(searchString);
		}
		// 커맨드
		String searchOper = req.getParameter("searchOper");
		if(searchOper!=null){
			dto.setSearchOper(searchOper);
		}
		
		ArrayList<SampleDto> list = sampleImpl.getSampleList(dto);
		HashMap<String, Object> result = new HashMap<String, Object>(); 
		result.put("list", list);
		result.put("page", dto.getPageNo());
		result.put("total", dto.getTotalPageCnt());
		if(logger.isInfoEnabled()){
			logger.info(JSONUtil.toJSON(result));
		}
		ajaxResponseJson(req, res, JSONUtil.toJSON(result));
		return null;
	}
	
	/**
	 * 목록
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws Exception{
		ArrayList<SampleDto> list = sampleImpl.getSampleList(dto);
		HashMap<String, Object> result = new HashMap<String, Object>(); 
		result.put("list", list);
		result.put("dto", dto);
		if(logger.isInfoEnabled()){
			logger.info(JSONUtil.toJSON(result));
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/list");
		mav.addObject("list", list);
		mav.addObject("dto", dto);
		return mav;
	}
	
	/**
	 * 조회 / 수정
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView view(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws Exception{
		SampleDto view = sampleImpl.getSample(dto);
		ArrayList<FileDto> fileList = fileImpl.getFileList(dto.getSeq());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/view");
		mav.addObject("view", view);
		mav.addObject("fileList", fileList);
		mav.addObject("dto", dto);
		return mav;
	}
	
	/**
	 * 등록 페이지
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ModelAndView write(HttpServletRequest req, HttpServletResponse res) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/write");
		return mav;
	}
	
	/**
	 * 등록 프로세스
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	public void doRegistFile(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws IOException{
		try{
			String seq = String.valueOf(sampleImpl.getNewSeq());
			dto.setSeq(seq);
			sampleImpl.insertSample(dto);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			res.sendRedirect("sample.do");
		}
	}
	
	/**
	 * Grid 등록 프로세스
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	public void doEditGrid(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws IOException{
		try{
			//추가 : sabun=1&name=1&oper=add&id=_empty
			//삭제 : oper=del&id=137
			String oper = req.getParameter("oper");
			String sabun = req.getParameter("sabun");
			String name = req.getParameter("name");
			String content = req.getParameter("content");
			String id = req.getParameter("id");
			
			if(oper!=null){
				if("add".equals(oper)){
					dto.setSabun(sabun);
					dto.setName(name);
					dto.setContent(content);
					String seq = String.valueOf(sampleImpl.getNewSeq());
					dto.setSeq(seq);
					sampleImpl.insertSample(dto);	
				}else if("del".equals(oper)){
					sampleImpl.deleteSample(id);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
	/**
	 * 수정 프로세스
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param dto SampleDto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ModelAndView modify(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dto") SampleDto dto) throws JSONException{
		HashMap<String, Object> result = new HashMap<String, Object>();
		String code = Constants.SUCCESS_CODE;
		String msg = "";
		try{
			sampleImpl.updateSample(dto);
		}catch (Exception e) {
			code = Constants.FAILURE_CODE;
			msg = e.getMessage();
		}finally{
			result.put("msg", msg);
			result.put("code", code);
			result.put("dto", dto);
			ajaxResponseHtml(req, res, JSONUtil.toJSON(result));
			return null;
		}
	}
	
	/**
	 * Grid 수정 프로세스
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public void modifyGrid(HttpServletRequest req, HttpServletResponse res){
		try{
			String id = req.getParameter("id");
			String cellName = req.getParameter("cellName"); 
			String cellValue = req.getParameter("cellValue");
			SampleDto dto = new SampleDto();
			dto.setSeq(id);
			if("sabun".equals(cellName)){
				dto.setSabun(cellValue);
				sampleImpl.updateSample(dto);
			}else if("name".equals(cellName)){
				dto.setName(cellValue);
				sampleImpl.updateSample(dto);
			}else if("content".equals(cellName)){
				dto.setContent(cellValue);
				sampleImpl.updateSample(dto);
			}
		}catch (Exception e) {
		}finally{
		}
	}
	
	/**
	 * 다운로드
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ModelAndView down(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String file_sq = (String)req.getParameter("file_sq");
		FileDto dto = (FileDto)fileImpl.getFileInfo(file_sq);
		
		byte[] buffer = new byte[1024];
		int byteData = 0;
		int offset = 0;
		ServletOutputStream out = null;
		FileInputStream in = null;

		try{
			if(dto != null){
				File f = new File(dto.getFile_path()+"/"+dto.getFile_physical_name());
				if(f.exists()){
					out = res.getOutputStream();
					res.setContentType("APPLICATION/STREAM");
					res.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(dto.getFile_physical_name(),"utf-8"));
					in = new FileInputStream(f);
					while((byteData = in.read(buffer, offset, buffer.length)) != -1){
						out.write(buffer, 0, byteData);
					}
					in.close();
					out.close();
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append("<script>");
					sb.append("alert('파일이 존재하지 않습니다.');");
					sb.append("history.back(-1);");
					sb.append("</script>");
					res.setContentType("text/html;charset=utf-8");
					res.getWriter().write(sb.toString());
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(in != null) in.close();
			if(out != null) out.close();
		}
		return null;		
	}
}
