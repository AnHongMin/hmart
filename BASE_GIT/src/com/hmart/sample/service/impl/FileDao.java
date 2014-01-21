package com.hmart.sample.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.hmart.sample.service.FileDto;

@Component
public class FileDao {
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMap;
	
	/**
	 * 첨부파일 정보 저장
	 * @param dto FileDto
	 * @throws Exception
	 * @author
	 */
	public void insertFile(FileDto dto) throws Exception{
		sqlMap.insert("File.insertFile", dto);
	}
	
	/**
	 * 첨부파일 리스트
	 * @param dto FileDto
	 * @return 첨부파일 리스트
	 * @throws Exception
	 * @author 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<FileDto> getFileList(FileDto dto) throws Exception{
		return (ArrayList<FileDto>)sqlMap.queryForList("File.getFileList", dto);
	}

	/**
	 * 첨부파일 정보
	 * @param file_sq String
	 * @return 첨부파일 정보
	 * @throws Exception
	 * @author
	 */
	public FileDto getFileInfo(String file_sq) throws Exception{
		return (FileDto)sqlMap.queryForObject("File.getFileInfo", file_sq);
	}
}
