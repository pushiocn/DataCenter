package org.pushio.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;



public class U extends StrUtil{


	
	public static final int DEFAULT_STREAM_BUF = 1024;
	private static final String DEFAULT_STRING_CHARSET = "utf-8";
	
	public static final DateFormat DF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static InputStream getInputStreamByFile(File file) throws FileNotFoundException{
		return new FileInputStream(file);
	}
	
	public static String getStringByInputStream(InputStream inputStream) throws IOException{
		String result = null;
		
		if(inputStream==null){
			return result;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[DEFAULT_STREAM_BUF];
		int readcnt = 0;
		while((readcnt = inputStream.read(buff)) >= 0){
			baos.write(buff, 0, readcnt);;
		}
		
		result = new String(baos.toByteArray(), DEFAULT_STRING_CHARSET);
		
		return result;
	}
	
	public static String getStringByFile(File file) throws IOException{
		InputStream inputStream = getInputStreamByFile(file);
		String ctx = getStringByInputStream(inputStream);
		return ctx;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		String fileName = "C:\\Users\\mmYaXin\\Documents\\wp\\testBoot\\src\\main\\java\\org\\pushio\\util\\";
//		System.out.println(getStringByInputStream(
//				new FileInputStream(fileName)));
		
		File f = new File(fileName);
		
		System.out.println(0+"0");
		 
		System.out.println(f.getPath());
	}

	public static void putStringToFile(String str, File destFile) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(destFile);
		try {
			fileOutputStream.write(str.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(null == fileOutputStream){
				fileOutputStream.close();
			}
		}
		
	}
	
	public static String getStringByResourcePath(String path) throws IOException{
		  InputStream scriptFileStream = U.class.getResourceAsStream(path);
	  String string = getCtxByStm(scriptFileStream, 1024, "utf-8");
	  scriptFileStream.close();
	  
	  return string;
	}
	
	public static Object getObj(String id, Map<String, Object> rt) {
		if (null == id) {
			return null;
		}
		String[] paths = id.split("\\.");

		Object data = rt;
		Object parent = null;
		for (int i = 0; i < paths.length; ++i) {
			String curKey = paths[i];
			
			if ((data instanceof Map)) {
				Map dataMap = (Map) data;
				
				
				parent = data;
				data = dataMap.get(curKey);
			} else if ((data instanceof List)) {
				parent = data;
				List<Object> list = (List<Object>)data;
				if (StrUtil.isNumeric(curKey)) {// 是数字
					Integer ix = Integer.parseInt(curKey);
					data = list.get(ix);
				}else{
					return null;
				}
			}
			
		}

		return data;
	}

}
