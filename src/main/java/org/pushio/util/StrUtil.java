package org.pushio.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;



public class StrUtil {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd mm:HH:ss");
	
	public static Pattern pattern = Pattern.compile("[0-9]*"); 
	
	public static boolean isNumeric(String str){ 
	    return pattern.matcher(str).matches();    
	 } 

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String str = "1234567";
		String subStr = "AA";
//		System.out.println(setCharsetInContentType("text/vnd.wap.wml; charset=utf-8", "gbk"));
//		String url = "http://g.hn165.com:80/";
//		System.out.println(getHostFromURL(url));
//		System.out.println(getMdn("1113711223344"));
		
//		String myStr = "\\u8bf7\\u6c42\\u505c\\\u8f66\u83b7\\u51c6\\u3002";
//		char[] strChars = myStr.toCharArray();
//		char[] buf = new char[1024];
//		String result = porp2Str(strChars, 0, strChars.length, buf);
		String urlStr = "http://iphone.myzaker.com/zaker/daily_hot.php";
		String result = getUrlRootPath(urlStr);
		System.out.println(result);
	}
	

	public static String tnull(String str){
		if(str == null){
			str = "";
		}
		return str;
	}
	
	public static String trim(String str){
		return tnull(str).trim();
	}
	
	public static String addSubStr(int ix, String str, String subStr){
		str = tnull(str);
		subStr = tnull(subStr);
		String result = "";
		StringBuilder strbd = new StringBuilder();
		strbd.append(str.substring(0, ix));
		strbd.append(subStr);
		strbd.append(str.substring(ix, str.length()));
		result = strbd.toString();
		return result;
	}	

	public static String setCharsetInContentType(String ctxType, String charset){
		//text/html; charset=GBK
		String nctxType = ctxType.trim().toLowerCase();
		String sChar = "charset=";
		int scharIx = ctxType.indexOf(sChar);
		if(scharIx < 0){
			return ctxType;
		}
		int sIx = scharIx + sChar.length();
		nctxType = ctxType.substring(0, sIx) + charset;
		return nctxType;
	}
	public static String trimAndToLow(String str){
		return trim(str).toLowerCase();
	}
	public static String trimAndToUpp(String str){
		return trim(str).toUpperCase();
	}
	
	public static byte[] ioCopyAndData(InputStream inds, OutputStream clOut, int buffSize) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] serBuff = new byte[buffSize];
		int r = 0;
		while((r = inds.read(serBuff)) != -1){
			baos.write(serBuff, 0, r);
			clOut.write(serBuff, 0, r);
		}
		byte[] data = baos.toByteArray();
		return data;
	}
	public static void ioCopy(InputStream inds, OutputStream clOut, int buffSize) throws IOException {
		byte[] serBuff = new byte[buffSize];
		int r = 0;
		while((r = inds.read(serBuff)) != -1){;
			clOut.write(serBuff, 0, r);
		}
	}
	public static void ioCopy(InputStream inds, OutputStream clOut, int buffSize, int dataSize) throws IOException {
		int rcnt = 0;
		byte[] serBuff = new byte[buffSize];
		int r = 0;
		while((r = inds.read(serBuff)) != -1){
			clOut.write(serBuff, 0, r);
			rcnt+=r;
			if(rcnt >= dataSize){
				break;
			}
		}
	}
	public static byte[] getDataByStm(InputStream inds, int buffSize, int dataSize) throws IOException {
		int rcnt = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] serBuff = new byte[buffSize];
		int r = 0;
		while((r = inds.read(serBuff)) != -1){
			baos.write(serBuff, 0, r);
			rcnt+=r;
			if(rcnt >= dataSize){
				break;
			}
		}
		return baos.toByteArray();
	}
	public static byte[] getDataByStm(InputStream inds, int buffSize) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] serBuff = new byte[buffSize];
		int r = 0;
		while((r = inds.read(serBuff)) != -1){
			baos.write(serBuff, 0, r);
		}
		return baos.toByteArray();
	}
	
	public static String getCtxByStm(InputStream inds, int buffSize, String charset) throws IOException {
		return getCtxByData(getDataByStm(inds, buffSize), charset);
	}
	public static String getCtxByFile(File file, int buffSize, String charset) throws IOException {
		return getCtxByData(getDataByStm(new FileInputStream(file), buffSize), charset);
	}

	public static byte[] gunzip(byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		GZIPInputStream gin = new GZIPInputStream(bais);
		data = getDataByStm(gin, 1024);
		return data;
	}
	
	public static String getHostFromURL(String url){
		//TODO 澶����url涓虹┖�����句�����://���寮�锟�??
		int start=url.indexOf("://")+3;
		if(start < 3){
			return "";
		}
		int end=url.indexOf('/',start);
		if(end==-1){
			url=url.substring(start);
		}else{
			url=url.substring(start,end);
		
		}
		//��ゆ�������㈡����������������凤����������峰��涓����姝ｇ‘���host
		int loc=url.indexOf('?');
		if(loc>1)url=url.substring(0,loc);
		//��ゆ��url�����㈡�����������?&锟�?
		loc=url.indexOf('&');
		if(loc>1)url=url.substring(0,loc);
		return url;
	}
	public static String getUrlCurPath(String url){
		String result = "";
		
		url = tnull(url);
		
		int ix = url.lastIndexOf("/");
		
		if((ix < 0) || ix >= url.length())return result;
		
		result = url.substring(0, ix+1);
		
		return result;
	}
	public static String getUrlRootPath(String url){
		String result = "";
		
		url = tnull(url);
		
		int ix = url.indexOf("//");
		if((ix < 0) || ix >= url.length())return result;
		
		int ix_ = url.indexOf("/", ix+2);
		if((ix_ < 0) || ix_ >= url.length())return result;
		
		result = url.substring(0, ix_);
		
		return result;
	}
	public static String fullLinkHref(String linkHref, String url){
		  String fullUrl = "";
		  linkHref = tnull(linkHref);
		 if(linkHref.startsWith("http")){
			  fullUrl = linkHref;
			  return fullUrl;
		  }else if(linkHref.startsWith("/")){
			  fullUrl = getUrlRootPath(url) + linkHref;
			  return fullUrl;
		  } if(!linkHref.startsWith("/")){
			  //当前路径
			  String curPath = getUrlCurPath(url);
			  fullUrl = curPath + linkHref;
			  return fullUrl;
		  }
		  return fullUrl;
	}
	
	public static byte[] getDataByFile(File file, int buffSize) throws IOException{
		FileInputStream fileInStm = new FileInputStream(file);
		byte[] data = getDataByStm(fileInStm, buffSize);
		fileInStm.close();
		return data;
	}
	
	public static String getCtxByData(byte[] data, String charset) throws UnsupportedEncodingException{
		String result = new String(data, charset);
		return result;
	}
	public static String getCtxByData(byte[] data) throws UnsupportedEncodingException{
		String result = new String(data, "utf-8");
		return result;
	}
	
	public static String getFileCtx(String filename, int buffSize, String charset) throws IOException{
		String result = getCtxByData(getDataByFile(new File(filename), buffSize), charset);	
		return result ;
	}
	public static void putCtxToFile(String fileName,String ctx, boolean append) throws IOException{
		File file = new File(fileName);
		file.createNewFile();
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, append));
		pw.print(ctx);
		pw.flush();
		pw.close();
	}
	/**
	 * 锟�?1浣���������������哄�风��?
	 * @param mdn �����哄�风��,锟�?6锟�?锟斤拷�����凤拷?
	 * @return 杩����?11浣������哄�凤�??
	 */
	public static String getMdn(String mdn){
		mdn = tnull(mdn).trim();
		if(mdn.length()>11){
			return mdn.substring(mdn.length()-11);
		}
		return mdn;
	}

	public static Long longNull(Long l) {
		if(null == l){
			l = new Long(0);
		}
		return l;
	}

	public static boolean isNone(String value) {
		if(value==null)return true;
		if("".equals(value.trim()))return true;
		return false;
	}
	public static Integer prIntByStr(String str){
		Integer result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	 public static String ascii2native(String str){
		char[] strChars = str.toCharArray();
		char[] buf = new char[1024];
		String result = porp2Str(strChars, 0, strChars.length, buf);
		return result;
	 }
	 public static String porp2Str (char[] in, int off, int len, char[] convtBuf) {
	        if (convtBuf.length < len) {
	            int newLen = len * 2;
	            if (newLen < 0) {
		        newLen = Integer.MAX_VALUE;
		    } 
		    convtBuf = new char[newLen];
	        }
	        char aChar;
	        char[] out = convtBuf; 
	        int outLen = 0;
	        int end = off + len;

	        while (off < end) {
	            aChar = in[off++];
	            if (aChar == '\\') {
	                aChar = in[off++];   
	                if(aChar == 'u') {
	                    // Read the xxxx
	                    int value=0;
			    for (int i=0; i<4; i++) {
			        aChar = in[off++];  
			        switch (aChar) {
			          case '0': case '1': case '2': case '3': case '4':
			          case '5': case '6': case '7': case '8': case '9':
			             value = (value << 4) + aChar - '0';
				     break;
				  case 'a': case 'b': case 'c':
	                          case 'd': case 'e': case 'f':
				     value = (value << 4) + 10 + aChar - 'a';
				     break;
				  case 'A': case 'B': case 'C':
	                          case 'D': case 'E': case 'F':
				     value = (value << 4) + 10 + aChar - 'A';
				     break;
				  default:
	                              throw new IllegalArgumentException(
	                                           "Malformed \\uxxxx encoding.");
	                        }
	                     }
	                    out[outLen++] = (char)value;
	                } else {
	                    if (aChar == 't') aChar = '\t'; 
	                    else if (aChar == 'r') aChar = '\r';
	                    else if (aChar == 'n') aChar = '\n';
	                    else if (aChar == 'f') aChar = '\f'; 
	                    out[outLen++] = aChar;
	                }
	            } else {
		        out[outLen++] = (char)aChar;
	            }
	        }
	        return new String (out, 0, outLen);
	    }
	 public static String getCtxByURL(String url)throws IOException{
		 return getCtxByURL(new URL(url));
	 }
	 public static String getCtxByURL(URL url)throws IOException{
		 return getCtxByHttpURL((HttpURLConnection)url.openConnection());
	 }
	 public static String getCtxByHttpURL(HttpURLConnection httpUrl) throws IOException{
		 String ctx = null;
		 
		 httpUrl.setDoOutput(true);
		 httpUrl.connect();
		 InputStream in = httpUrl.getInputStream();
		 
		 String charset = httpUrl.getContentEncoding();
		 if("gzip".equals(charset)){
			 in = new GZIPInputStream(in);
		 }
		 
		ctx = getCtxByStm(in, 1024, "utf-8" );
		 
		 return ctx;
	 }
	 
	 public static String trIntegerArrToStr(Integer[] nums){
		 StringBuilder strBr = new StringBuilder();
		 
		 for(int i = 0; i < nums.length; ++i){
			 strBr.append(nums[i]).append(",");
		 }
		 strBr.substring(0, strBr.length());
			 
		return strBr.toString();
	 }
	 
	 public static Long str2Long(String str, Long def){
		 Long result = def;
		 try{
			 result = Long.parseLong(str);
		 }catch(Exception excep){
			 //excep.printStackTrace();
			 
		 }
		 return result;
	 }
	 public static Integer str2Int(String str, Integer def){
		 Integer result = def;
		 try{
			 result = Integer.parseInt(str);
		 }catch(Exception excep){
			 //excep.printStackTrace();
			 
		 }
		 return result;
	 }
}
