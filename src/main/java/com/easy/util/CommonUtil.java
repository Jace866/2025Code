package com.easy.util;

import java.util.UUID;

public class CommonUtil {
	//UUID含义是通用唯一识别码 (Universally Unique Identifier)
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toUpperCase();
	}
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		System.out.println("uuid="+getUUID());
	}

	public static String getBrowserName(String agent) {
		agent=agent.toLowerCase();
		if (agent.indexOf("msie 9") > 0) {
			return "IE9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "IE10";
		} else if (agent.indexOf("msie 8") > 0) {
			return "IE8";
		} else if (agent.indexOf("msie") > 0) {
			return "IE";
		} else if (agent.indexOf("opera") > 0) {
			return "OPERA";
		} else if (agent.indexOf("opr/") > 0) {
			return "OPERA";
		} else if (agent.indexOf("crios/") > 0) {
			return "火狐";
		} else if (agent.indexOf("firefox") > 0) {
			return "火狐";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "IE11";
		} else if (agent.indexOf("iphone") > 0) {
			return "苹果";
		} else if (agent.indexOf("android") > 0) {
			return "安卓";
		} else if (agent.indexOf("surface") > 0) {
			return "微软";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else {
			return "其它";
		}
	}

}
