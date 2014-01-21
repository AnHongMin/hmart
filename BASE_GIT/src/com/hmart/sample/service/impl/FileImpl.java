
package com.hmart.sample.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hmart.sample.service.FileDto;
import com.hmart.sample.service.FileService;

/**
 * 파일처리
 * @author
 */
@Component
public class FileImpl implements FileService{
	@Resource(name="fileDao")
	private FileDao fileDao;
	@Resource(name="configProperties")
	Properties prop;
	
	public FileImpl(){}
	
	public static String separator = System.getProperty("file.separator");
	
	@Override
	public boolean saveFileInfo(String seq, String path, MultipartFile multipartFile) throws Exception {
		boolean result = false;
		if(!multipartFile.isEmpty()) {
		    try {
		        // file 테이블에 저장
		        FileDto fd = getSaveFile(path,multipartFile);
		        fd.setSeq(seq);
				fileDao.insertFile(fd);
		        result = true;
		    } catch(Exception e) {
		        result = false;
		    }
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean saveFileListInfo(String seq,String path,ArrayList<MultipartFile> fileList) throws Exception{
		boolean result = false;
		if(fileList != null) {
		    try {
		    	int size = fileList.size();
		    	for(int i=0;i<size;i++){
			        // file 테이블에 저장
			        FileDto fd = getSaveFile(path,fileList.get(i));
			        fd.setSeq(seq);
			        if(fd.getFile_physical_name()!=null){
			        	fileDao.insertFile(fd);
			        }
			        result = true;
		    	}
		    } catch(Exception e) {
		        result = false;
		    }
		}
		return result;
	}
	
	@Override
	public FileDto getSaveFile(String file_path, MultipartFile multipartFile) throws Exception {
		FileDto result = new FileDto();
		String save_path = prop.getProperty("common.upload_path");	// 기본 업로드 경로
		
		// 파일이 있다면
		if(!multipartFile.isEmpty()){
			String fileName = multipartFile.getOriginalFilename();
			// 경로가 넘어오면 넘어온 경로로 대체
			if(!ObjectUtils.toString(file_path).equals("")) save_path = save_path+separator+file_path;
			File dir = new File(save_path);
	        if(!dir.exists()) {
	        	dir.mkdirs();
	        }
	        File file = new File(dir,fileName);
	        int idx = 1;
	        while(file.exists()){
	        	String tmp = idx+"_"+fileName;
	        	file = new File(dir,tmp);
	        	idx++;
	        }
	        multipartFile.transferTo(file);
	        
			result.setFile_path(save_path);
			result.setFile_logical_name(fileName);
	        result.setFile_physical_name(file.getName());
	        result.setFile_size(multipartFile.getSize());
	        result.setFile_type(multipartFile.getContentType());
		}
		return result;
		
	}

	/**
	 * 첨부파일 리스트
	 */
	public ArrayList<FileDto> getFileList(String seq) throws Exception {
		FileDto dto = new FileDto();
		dto.setSeq(seq);
		return fileDao.getFileList(dto);
	}
	
	/**
	 * 첨부파일 정보
	 */
	public FileDto getFileInfo(String file_sq) throws Exception {
		return (FileDto)fileDao.getFileInfo(file_sq);
	}
}
