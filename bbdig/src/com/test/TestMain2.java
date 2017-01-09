package com.test;

import org.apache.commons.lang3.StringUtils;

import com.bbdig.core.util.ConnectUtil;
import com.bbdig.timer.TiebaBdyTask;

public class TestMain2 {

	public static void main(String[] args) {
		
	String resStr = ConnectUtil.connect("https://www.baidu.com/s?wd=site%3Awebhezi.com&pn=170", "utf-8");
		System.out.println(resStr);
	}

}
