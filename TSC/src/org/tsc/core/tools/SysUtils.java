package org.tsc.core.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.RETURN;
/**
 * 
 * 系统工具类
 * 
 * 
 */
public class SysUtils {

	/**
	 * 将对象转换为json传给浏览器
	 */
	public static void toJson(HttpServletResponse response,Object object) {
		Gson gson = new Gson();
		response.setContentType("text/html");
		response.setContentType("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(gson.toJson(object));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  获取某个字符串中相同字符的第n个之后的子字符串        比如（abd,gbeds,kk,jdf）获取第2个逗号之后或之前的子字符串
	 *	如果没有该字符 则返回原字符串
	 */
	public static String getSubString(String str,char ch,int i,int index) {
		int count = 0;
		char[] chs = str.toCharArray();
		String str1 = null;
		for (int j = 0; j < chs.length; j++) {
			if (chs[j] == ch) {
				count++;
				if (count == i) {
					if (index == 0) {
						str1 = str.substring(index ,j);					
						System.out.println("前半段 = "+str1);
					}else {
						str1 = str.substring(j+1);
						System.out.println("后半段 = "+str1);
					}
					break;
				}
			}else {
				str1 = str;
			}
		}
		return str1;
	}
	/**
	 * 产生指定长度的数字字符串
	 * 附：字符串仅包含了 数字（0~9）
	 * @param length	数字字符串的长度
	 * 
	 */
	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * 将字符串转化为日期
	 * 
	 */
	public static Date strToDate(String string) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将日期转化为字符串格式
	 *
	 */
	public static String dateToStr(Date date) {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		String string = sFormat.format(date);
		return string;
	}
}
