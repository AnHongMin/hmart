package com.hmart.sample.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FileDto implements Serializable{
	// 파일키
	private String file_sq;
	// 파일 구분키
	private String seq;
	// 물리적 파일명
	private String file_physical_name;
	// 논리적 파일명
	private String file_logical_name;
	// 파일사이즈
	private long file_size;
	// 파일형식
	private String file_type;
	// 파일경로
	private String file_path;
	
	public String getFile_sq() {
		return file_sq;
	}
	public void setFile_sq(String file_sq) {
		this.file_sq = file_sq;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getFile_physical_name() {
		return file_physical_name;
	}
	public void setFile_physical_name(String file_physical_name) {
		this.file_physical_name = file_physical_name;
	}
	public String getFile_logical_name() {
		return file_logical_name;
	}
	public void setFile_logical_name(String file_logical_name) {
		this.file_logical_name = file_logical_name;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	
}
