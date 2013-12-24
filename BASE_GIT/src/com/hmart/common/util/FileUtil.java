package com.hmart.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 파일&디렉토리의 copy,delete,move,mkdir,exists 메소드.
 *
 * @author 안홍민
 * @version 1.0
 */
public class FileUtil {

	/** logger. */
	static Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 파일&디렉토리 복사 메소드.
	 *
	 * @param source 원본 파일&디렉토리.
	 * @param target 타겟 파일&디렉토리.
	 * @return boolean 복사 성공 여부.
	 */
	public static boolean copy(String source, String target) {
		File sourceFile = new File(source);
		File targetFile = new File(target);
		return copy(sourceFile, targetFile);
	}

	/**
	 * 파일&디렉토리 복사 메소드.
	 *
	 * @param source 원본 파일&디렉토리.
	 * @param target 타겟 파일&디렉토리.
	 * @return boolean 복사 성공 여부.
	 */
	public static boolean copy(File source, File target) {
		boolean sucess = false;
		if (source.exists()) {
			if (source.isDirectory()) {
				target.mkdir();
				File list[] = source.listFiles();
				for (int i = 0; i < list.length; i++) {
					File tempFile = new File(target.getPath() + File.separator + list[i].getName());
					sucess = copy(list[i], tempFile);
					if (!sucess)
						break;
				}
				sucess = target.exists();
			} else {
				sucess = copyFile(source, target);
			}
		}
		return sucess;
	}

	/**
	 * 파일 복사 메소드<br>
	 * copy(File source, File target) 를 써도된다.
	 *
	 * @param source
	 *            원본 파일.
	 * @param target
	 *            타겟 파일.
	 * @return boolean 복사 성공 여부.
	 */
	public static boolean copyFile(File source, File target) {
		if (!source.exists() || !source.isFile()) {
			return false;
		}

		byte[] buffer = new byte[4096];
		int bytesRead;
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new FileInputStream(source));
			output = new BufferedOutputStream(new FileOutputStream(target));
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
				}
			}
		}
		return target.exists();
	}

	/**
	 * 파일&디렉토리 삭제 메소드.
	 *
	 * @param source 삭제 파일&디렉토리.
	 * @return boolean 삭제 성공 여부.
	 */
	public static boolean delete(String source) {
		File sourceFile = new File(source);
		return delete(sourceFile);
	}

	/**
	 * 파일&디렉토리 삭제 메소드.
	 *
	 * @param source 삭제 파일&디렉토리.
	 * @return boolean 삭제 성공 여부.
	 */
	public static boolean delete(File source) {
		boolean sucess = false;
		if (source.exists()) {
			if (source.isDirectory()) {
				File list[] = source.listFiles();
				for (int i = 0; i < list.length; i++) {
					File tempFile = new File(source.getPath() + File.separator + list[i].getName());
					sucess = delete(tempFile);
					if (!sucess)
						break;
				}
				sucess = source.delete();

			} else {
				sucess = source.delete();
			}
		}
		return sucess;
	}

	/**
	 * 파일&디렉토리 이동메소드. <br>
	 * 타겟이 존재하고 디렉토리일경우 내용을 복사해 넣은후 원본 삭제.
	 *
	 * @param source
	 *            원본 파일.
	 * @param target
	 *            타겟 파일.
	 * @return boolean 삭제 성공 여부.
	 */
	public static boolean move(String source, String target) {
		File sourceFile = new File(source);
		File targetFile = new File(target);
		return move(sourceFile, targetFile);
	}

	/**
	 * 파일&디렉토리 이동메소드. <br>
	 * 타겟이 존재하고 디렉토리일경우 내용을 복사해 넣은후 원본 삭제.
	 *
	 * @param source
	 *            원본 파일.
	 * @param target
	 *            타겟 파일.
	 * @return boolean 삭제 성공 여부.
	 */
	public static boolean move(File source, File target) {
		boolean sucess = false;
		if (!source.exists()) {
			return false;
		} else if (target.exists() && target.isDirectory()) {
			if (copy(source, target)) {
				sucess = delete(source);
			}
		} else {
			if(target.exists()) delete(target);
			sucess = source.renameTo(target);
		}
		return sucess;
	}

	/**
	 * 폴더, 파일 이름 변경(-1:실패, 0:성공, 1:파일,폴더 존재.
	 *
	 * @param oldFile File
	 * @param newFile File
	 * @return the int
	 */
	public static int rename(File oldFile, File newFile) {
		if (newFile.exists())
			return 1;
		if (oldFile.renameTo(newFile))
			return 0;
		return -1;
	}

	/**
	 * Rename.
	 *
	 * @param oldname String
	 * @param newname String
	 * @return true, if successful
	 */
	public static boolean rename(String oldname, String newname) {
		File oldFile = new File(oldname);
		File newFile = new File(newname);
		return move(oldFile, newFile);
	}

	/**
	 * 디렉토리 생성메소드. <br>
	 * 타겟이 존재하고 디렉토리가 아닐경우 타겟을 삭제후 디렉토리 생성.
	 *
	 * @param target
	 *            생성될 디렉토리.
	 * @return boolean 생성 성공 여부.
	 * @deprecated
	 */
	public static boolean mkdir(String target) {
		File targetDir = new File(target);
		return mkdir(targetDir);
	}

	/**
	 * 디렉토리 생성메소드. <br>
	 * 타겟이 존재하고 디렉토리가 아닌경우 타겟을 삭제후 디렉토리 생성.
	 *
	 * @param target
	 *            생성될 디렉토리.
	 * @return boolean 생성 성공 여부.
	 * @deprecated
	 */
	@SuppressWarnings("null")
	public static boolean mkdir(File target) {
		boolean sucess = false;
		if (target.exists() && !target.isDirectory()) {
			if (delete(target)) {
				sucess = target.mkdir();
			}
		}

		if (!target.exists()) {
			String path = target.getPath();
			String paths[] = null;
			if (path.indexOf("/") != -1) {
				// paths = path.split("/");
			} else {
				// paths = path.split("\\\\");
			}

			String tempPath = "";
			for (int i = 0; i < paths.length; i++) {
				tempPath += paths[i] + File.separator;
				File temp = new File(tempPath);
				if (!temp.exists()) {
					temp.mkdir();
				}
			}
		}
		sucess = target.exists();
		return sucess;
	}

	/**
	 * 디렉토리 존재 검사. <br>
	 *
	 * @param source
	 *            파일&디렉토리.
	 * @return boolean true=존재.
	 */
	public static boolean exists(String source) {
		File sourceDir = new File(source);
		return exists(sourceDir);
	}

	/**
	 * 디렉토리 존재 검사. <br>
	 *
	 * @param source
	 *            파일&디렉토리.
	 * @return boolean true=존재.
	 */
	public static boolean exists(File source) {
		return source.exists();
	}

	/**
	 * 숫자를 단위로(Byte,KB,MB,GB).
	 *
	 * @param num long
	 * @return the string
	 */
	public static String strNumToFileSize(long num) {
		String ret = "";
		int bias = 1024;

		if (num < bias) {
			ret = num + "Byte";
		} else if (num >= bias && num < (bias * bias)) {
			ret = num / bias + "KB";
		} else if (num >= (bias * bias) && num < (bias * bias * bias)) {
			ret = num / (bias * bias) + "MB";
		} else if (num >= (bias * bias * bias) && num < (bias * bias * bias * bias)) {
			ret = num / (bias * bias * bias) + "GB";
		}
		return ret;
	}

	/**
	 * 숫자를 단위(Byte,KB,MB,GB)로 변환.
	 *
	 * @param num int
	 * @return the string
	 */
	public static String strNumToFileSize(int num) {
		return strNumToFileSize((long) num);
	}

	/**
	 * 입력받은 파일 이름에서 파일 확장자를 추출한다.
	 *
	 * @param fileName
	 *            파일 명
	 * @return String 입력받은 파일 명에서 파일 확장자를 추출하여 반환 하거나 입력받은 파일 명이 null이라면 null을
	 *         확장자를 추출할수 없다면 빈문자열을 반환한다.
	 */
	public static String getFileType(String fileName) {
		String fileExt = null;
		if (fileName != null) {
			int offset = fileName.lastIndexOf(".");
			if ((offset != -1) && (offset != fileName.length())) {
				fileExt = fileName.substring(offset + 1);
			} else {
				fileExt = "";
			}
		}

		return fileExt;
	}

	/**
	 * 입력받은 파일 이름에서 파일 확장자를 추출한다.
	 *
	 * @param filePath String
	 * @return String 입력받은 파일 명에서 파일 확장자를 추출하여 반환 하거나 입력받은 파일 명이 null이라면 null을
	 * 확장자를 추출할수 없다면 입력받은 파일 명을 반환한다.
	 */
	public static String getFileName(String filePath) {
		String fileName = null;
		if (filePath != null) {
			int offset = filePath.lastIndexOf("/");
			if ((offset != -1) && (offset != filePath.length())) {
				fileName = filePath.substring(offset + 1);
			} else {
				fileName = filePath;
			}
		}

		return fileName;
	}

	/**
	 * JSON 파일 만들기.
	 *
	 * @param strFilePath String
	 * @param jsonNode String
	 * @return true, if successful
	 */
	public boolean makeJSONFile(String strFilePath, String jsonNode) {
		return makeFile(strFilePath, "[" + jsonNode + "]", "UTF-8");
	}

	/**
	 * JSON 파일 만들기.
	 *
	 * @param strFilePath String
	 * @param jsonNode String
	 * @param strEncoding String
	 * @return true, if successful
	 */
	public boolean makeJSONFile(String strFilePath, String jsonNode, String strEncoding) {
		return makeFile(strFilePath, "[" + jsonNode + "]", strEncoding);
	}

	/**
	 * 파일 생성.
	 *
	 * @param baseUrl String
	 * @param mode String
	 * @param year String
	 * @param month String
	 * @param date String
	 * @param fileName String
	 * @param body String
	 * @return true, if successful
	 */
	public static boolean makeFile(String baseUrl, String mode, String year, String month, String date, String fileName, String body) {
		String sep = System.getProperty("file.separator");
		StringBuffer url = new StringBuffer();
		url.append(url).append(sep).append(mode).append(sep).append(year).append(sep).append(month).append(sep).append(date).append(sep).append(
				fileName);
		return makeFile(url.toString(), body, "UTF-8");
	}

	/**
	 * 파일 생성.
	 *
	 * @param strFilePath String
	 * @param body String
	 * @return true, if successful
	 */
	public static boolean makeFile(String strFilePath, String body) {
		return makeFile(strFilePath, body, "UTF-8");
	}

	/**
	 * 파일 생성.
	 *
	 * @param strFilePath String
	 * @param body String
	 * @param strEncoding String
	 * @return true, if successful
	 */
	public static boolean makeFile(String strFilePath, String body, String strEncoding) {
		boolean isDone = false;
		File fOut = null;
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fOut = new File(strFilePath);
			fileOutputStream = new FileOutputStream(fOut);
			if (strEncoding != null && !"".equals(strEncoding)) {
				outputStreamWriter = new OutputStreamWriter(fileOutputStream, strEncoding);
			} else {
				outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			}
			bufferedWriter = new BufferedWriter(outputStreamWriter);

			bufferedWriter.write(body);
			bufferedWriter.newLine();

			bufferedWriter.flush();

			isDone = true;
		} catch (FileNotFoundException fnfe) {
			logger.error("", fnfe);
		} catch (UnsupportedEncodingException uee) {
			logger.error("strFilePath=" + strFilePath, uee);
			disconnectOut(fileOutputStream, outputStreamWriter, bufferedWriter);
			fOut.delete();
		} catch (IOException ioe) {
			logger.error("", ioe);
		} finally {
			disconnectOut(fileOutputStream, outputStreamWriter, bufferedWriter);
		}

		return isDone;
	}

	/**
	 * 지정한 파일을 지정한 인코딩으로 읽어들여 문자열로 리턴
	 *
	 * @param strFilePath String
	 * @param strEncoding String
	 * @return body
	 */
	public static String getBodyFile(String strFilePath, String strEncoding) {
		BufferedReader br = null;
		StringBuffer body = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath), strEncoding));

			String str = null;
			while (true) {
				str = br.readLine();
				if (str == null) {
					break;
				}
				body.append(str);
			}
		} catch (FileNotFoundException e) {
			logger.error("그런 파일이 없습니다(No such file or directory).["+strFilePath+"]");
		} catch (IOException e) {
			logger.error("예외 발생...");
		} finally{
			if(br != null)try{ br.close(); }catch(Exception e){}
		}

		return body.toString();
	}

	/**
	 * 지정한 파일을 지정한 인코딩으로 읽어들여 arrayList로 리턴
	 *
	 * @param strFilePath String
	 * @param strEncoding String
	 * @return the body file nl
	 */
	public static String getBodyFileNL(String strFilePath, String strEncoding) {
		BufferedReader br = null;
		StringBuffer body = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath), strEncoding));

			String str = null;
			while (true) {
				str = br.readLine();
				if (str == null) {
					break;
				}
				body.append(str).append("\n");
			}
		} catch (FileNotFoundException e) {
			logger.error("그런 파일이 없습니다.", e);
		} catch (IOException e) {
			logger.error("예외 발생...", e);
		} finally{
			if(br != null)try{ br.close(); }catch(Exception e){}
		}

		return body.toString();
	}


	/**
	 * 지정한 파일을 읽어들여 arrayList로 리턴
	 *
	 * @param strFilePath String
	 * @return the body file
	 */
	public static String getBodyFile(String strFilePath) {
		FileReader rd = null;
		BufferedReader br = null;

		StringBuffer body = new StringBuffer();

		try {
			rd = new FileReader(strFilePath);
			br = new BufferedReader(rd);

			String str = null;
			while (true) {
				str = br.readLine();

				if (str == null) {
					break;
				}
				body.append(str);
			}
		} catch (FileNotFoundException e) {
			logger.error("그런 파일이 없습니다.", e);
		} catch (IOException e) {
			logger.error("예외 발생...", e);
		} finally{
			if(br != null)try{ br.close(); }catch(Exception e){}
		}

		return body.toString();
	}

	/**
	 * 지정한 파일을 지정한 인코딩으로 읽어들여 arrayList로 리턴
	 *
	 * @param strFilePath String
	 * @param strEncoding String
	 * @return body
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static ArrayList getBodyFileToArray(String strFilePath,String strEncoding) {
		BufferedReader br = null;

		ArrayList body = new ArrayList();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(strFilePath), strEncoding));

			String str = null;
			long count = 0;
			while (true) {
				str = br.readLine();

				if (str == null) {
					break;
				}
				body.add(str);
			}
		} catch (FileNotFoundException e) {
			logger.error("그런 파일이 없습니다.", e);
		} catch (IOException e) {
			logger.error("예외 발생...", e);
		} finally{
			if(br != null)try{ br.close(); }catch(Exception e){}
		}
		return body;
	}

	/**
	 * 파일 스트림 닫기.
	 *
	 * @param fileOutputStream FileOutputStream
	 * @param outputStreamWriter OutputStreamWriter
	 * @param bufferedWriter BufferedWriter
	 */
	public static void disconnectOut(FileOutputStream fileOutputStream, OutputStreamWriter outputStreamWriter, BufferedWriter bufferedWriter) {
		if (fileOutputStream != null) {
			try {
				fileOutputStream.close();
				bufferedWriter = null;
			} catch (IOException e) {
				logger.error("", e);
			}
		}

		if (outputStreamWriter != null) {
			try {
				outputStreamWriter.close();
				outputStreamWriter = null;
			} catch (IOException e) {
				logger.error("", e);
			}
		}

		if (bufferedWriter != null) {
			try {
				bufferedWriter.close();
				bufferedWriter = null;
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

		/**
	 * 폴더를 생성한다.
	 *
	 * @param dirPath String
	 */
	public static void makeFolder(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}


	/**
	 * 파일url에서 파일명앞에 문자를 추가한다.
	 *
	 * @param fileUrl String
	 * @param prefix String
	 * @return the string
	 */
	public static String addPrefixToFileName(String fileUrl, String prefix){
		if(fileUrl==null) return null;

		StringBuffer sb = new StringBuffer(fileUrl);
		sb.insert(sb.lastIndexOf("/")+1, prefix);
		return sb.toString();
	}

	/**
	 * 파일url에서 파일명뒤에 문자를 추가한다.
	 *
	 * @param fileUrl String
	 * @param postfix String
	 * @return the string
	 */
	public static String addPostfixToFileName(String fileUrl, String postfix){
		if(fileUrl==null) return null;

		StringBuffer sb = new StringBuffer(fileUrl);
		int dotIdx = sb.lastIndexOf(".", sb.lastIndexOf("/")+1);
		if(dotIdx==-1){
			sb.append(postfix);
		}else{
			sb.insert(dotIdx-1, postfix);
		}
		return sb.toString();
	}

	/**
	 * 유효한 파일명을 리턴한다.
	 *
	 * @param fileName String
	 * @return the valid file name
	 */
	public static String getValidFileName(String fileName){
		return StringUtils.replace(fileName.trim().replaceAll("[/:*?<>\"|]",""), "\\", "");
	}

	/**
	 * 특정길이의 유효한 파일명을 리턴한다.
	 *
	 * @param fileName String
	 * @param maxLength int
	 * @return the valid file name
	 */
	public static String getValidFileName(String fileName, int maxLength){
		String validFileName = getValidFileName(fileName);
		if(validFileName.length()>maxLength) return validFileName.substring(0, maxLength);
		return validFileName;
	}

	/**
	 * 파일명 제거
	 *
	 * @param name String
	 * @return 문자열
	 */
	public static String getCleanFileName(String name){
		return name.replaceAll("\"", "").replaceAll("/", "").replaceAll(":", "").replaceAll("\\?", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll("\"\"", "").replaceAll("<", "").replaceAll(">", "");
	}

	/**
	 * 중복 dot(마침표) 제거
	 *
	 * @param srcStr String
	 * @return 문자열
	 */
	public static String removeDoublePeriod(String srcStr){
		while (srcStr.indexOf("..") > -1){
			srcStr = srcStr.replaceAll("\\.\\.","\\.");
		}
		return srcStr;
	}
	
	/**
	 * mime 파일 경로 정보로  hwp 파일을 지정된 경로에 생성한다.
	 *
	 * @param fileFullPath		mime 경로정보
	 * @param changeFilePath	hwp 경로 정보
	 * @return
	 */
	public static boolean makeHwpFile(String fileFullPath, String changeFilePath) {
		boolean isDone = false;
		
		FileReader			rd	= null;
		BufferedReader		br	= null;
		FileOutputStream	fo	= null;

		
		StringBuffer	body	= new StringBuffer();

		try {
			rd	= new FileReader(fileFullPath);
			br	= new BufferedReader(rd);

			String	str	= null;
			while (true) {
				str	= br.readLine();

				if (str == null) {
					break;
				}

				body.append(str);
			}

			// byte 단위로 파라미터를 주는 것이 정확함
			byte[]	text	= Base64.decodeBase64(body.toString().getBytes());

			fo	= new FileOutputStream(changeFilePath);
			fo.write(text, 0, text.length);

			logger.info("Convert " + fileFullPath + " to " + changeFilePath + " Finished.");
		} catch (FileNotFoundException e) {
			System.out.println("Convert " + fileFullPath + " to " + changeFilePath + " Failed.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Convert " + fileFullPath + " to " + changeFilePath + " Failed.");
			e.printStackTrace();
		} finally {
			try {
				if (null != fo) {
					fo.close();
					fo	= null;
				}

				if (null != br) {
					br.close();
					br	= null;
				}

				if (null != rd) {
					rd.close();
					rd	= null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		return isDone;
	}
	
	/**
	 * 현재일 부터 정한 날짜 이전 파일&디렉토리 삭제 메소드.
	 *
	 * @param source 삭제 파일&디렉토리.
	 * @param date 기간 지정
	 * @return boolean 삭제 성공 여부.
	 */
	public static boolean deleteSetPeriod(File source, int date) {
		boolean sucess = false;
		
		if (source.exists()) {
			
			if (source.isDirectory()) {
				File list[] = source.listFiles();
				for (int i = 0; i < list.length; i++) {
					File tempFile = new File(source.getPath() + File.separator + list[i].getName());
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					
					// 파일의 마지막변경일자
					Date fileDate = new Date(tempFile.lastModified());
					String fileDateStr 		= formatter.format(fileDate);
						
					// 현재일자에서 date를 빼준다.
					Calendar currentCal = Calendar.getInstance();
					currentCal.add(Calendar.DATE, date);
					String currentDateStr 	= formatter.format(currentCal.getTime());
					
					// 현재일자 - date 보다 이전 파일들은 삭제한다.
					if(Integer.parseInt(fileDateStr) < Integer.parseInt(currentDateStr)){
						sucess = delete(tempFile);
						if (!sucess)
							break;
					}
				}
			}	
		}
		return sucess;
	}
	
	/**
	 * 폴더를 생성한다.(상위폴더까지)
	 *
	 * @param dirPath String
	 */
	public static void makeFolders(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
