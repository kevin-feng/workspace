package org.tsc.core.tools;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

import com.sun.org.glassfish.external.statistics.Statistic;

public class MD5 {

	public static String md5Encode(String parameter) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] digestResult = md.digest(StringUtils.defaultString(parameter).getBytes("UTF-8"));

			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < digestResult.length; i++) {
				int val = ((int) digestResult[i]) & 0xff;
				hexValue.append(StringUtils.leftPad(Integer.toHexString(val),2, '0'));
			}
			return hexValue.toString().toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return parameter;
	}
}
