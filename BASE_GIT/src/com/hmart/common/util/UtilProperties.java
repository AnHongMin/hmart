package com.hmart.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilProperties extends Properties {

	private static final long serialVersionUID = -8590550760406564196L;

	static String CONF_FILE_PATH = ""; // 파일경로명
	static File fileCONF_FILE = null; // 파일객체
	long lastModified = 0; // 수정여부
	static Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}"); // ${key}

	// 인스턴스 선언
	private static UtilProperties instance = new UtilProperties();

	/**
	 * 인스턴스 가지고 오기
	 * 
	 * @return
	 */
	public static UtilProperties getInstance() {

		// 파일객체가 존재하지 않으면 가지고 옵니다.
		if (fileCONF_FILE == null) {
			setConfFilePath(System.getProperty("CONF_FILE_PATH"));
		}

		// 파일객체가 그래도 없으면 예외로 처리 합니다.
		if (fileCONF_FILE == null) {
			throw new RuntimeException("Conf file is not set.");
		}

		// 수정일을 가지고 옵니다.
		long lastModified = fileCONF_FILE.lastModified();

		// 수정일이 변경되었으면 다시 불러 옵니다.
		if (instance.lastModified != lastModified) {
			try {
				instance.reload();
				instance.lastModified = lastModified;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * 설정 파일에서 reload
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void reload() throws IOException {
		FileInputStream fis = null; // FileInputStream

		try {
			String key = ""; // Key 임시변수

			// Properties 파일을 읽어옵니다.
			fis = new FileInputStream(CONF_FILE_PATH);
			// Properties 파일을 로드 합니다.
			load(fis);
			// Properties 파일에서 정보를 가지고 옵니다.
			Enumeration enum1 = this.keys();

			// Properties 파일에서 정보 만큼 가지고 와서 저장합니다.
			while (enum1.hasMoreElements()) {
				key = (String) enum1.nextElement();
				remake(key);
			}
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * 재저장
	 * 
	 * @param key
	 * @return
	 */
	String remake(String key) {
		// 키에대한 값을 가지고 옵니다.
		String val = this.getProperty(key);
		Matcher matcher = pattern.matcher(val);
		StringBuffer sb = new StringBuffer();
		String val2, key2;

		int changedCnt = 0;

		while (matcher.find()) {
			changedCnt++;
			key2 = matcher.group(1);
			val2 = this.getProperty(key2);
			if (val2 != null && pattern.matcher(val2).find()) {
				val2 = remake(key2);
			}
			matcher.appendReplacement(sb, UtilProperties.nvl(val2));
		}
		matcher.appendTail(sb);
		String changedVal = sb.toString().trim();
		if (changedCnt > 0) {
			this.setProperty(key, changedVal);
			return changedVal;
		} else {
			return val;
		}
	}

	/**
	 * Properties 파일 설정
	 * 
	 * @param conf_file_path
	 */
	public static void setConfFilePath(String conf_file_path) {
		CONF_FILE_PATH = conf_file_path;
		fileCONF_FILE = new File(CONF_FILE_PATH);
		System.setProperty("CONF_FILE_PATH", CONF_FILE_PATH);
	}

	/**
	 * String이 Null이면 공백으로 리턴
	 * 
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {
		return nvl(str, "");
	}

	/**
	 * null 이면 지정한 값으로 변경
	 * 
	 * @param src
	 * @param trg
	 * @return
	 */
	public static String nvl(Object src, String trg) {
		if (src == null) {
			return trg.trim();
		} else {
			return src.toString().trim();
		}
	}
}