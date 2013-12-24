package com.hmart.sample.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 파일
 * 
 * @author 안홍민
 *
 */
public interface FileService {
	/**
	 * 첨부파일 저장(스프링용)
	 * @param file_path String
	 * @param multipartFile MultipartFile
	 * @return
	 * @throws Exception
	 */
	public FileDto getSaveFile(String file_path, MultipartFile multipartFile) throws Exception;
	
	/**
	 * 첨부파일 정보 저장(스프링용)
	 * @param seq String
	 * @param path String
	 * @param multipartFile MultipartFile
	 * @return
	 * @throws Exception
	 */
	public boolean saveFileInfo(String seq, String path, MultipartFile multipartFile) throws Exception;
	
	/**
	 * 여거개의 첨부파일 정보 저장(스프링용)
	 * @param seq String
	 * @param path String
	 * @param fileList ArrayList<MultipartFile>
	 * @return
	 * @throws Exception
	 */
	public boolean saveFileListInfo(String seq, String path, ArrayList<MultipartFile> fileList) throws Exception;
	
	/**
	 * 첨부파일 리스트
	 * @param seq String
	 * @return
	 * @throws Exception
	 * @author
	 */
	public ArrayList<FileDto> getFileList(String seq) throws Exception;
	
	/**
	 * 첨부파일 정보
	 * @param seq String
	 * @return
	 * @throws Exception
	 * @author
	 */
	public FileDto getFileInfo(String seq) throws Exception;
}
