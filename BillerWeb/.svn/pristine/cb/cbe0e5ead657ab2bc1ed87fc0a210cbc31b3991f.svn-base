package com.dtac.billerweb.test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAll {

	public void test(){
		String a=null;
		if("".equals(a)){
			System.out.println("IN:"+a);
		}else{
			System.out.println("Out:"+a);
		}
	}
	public void test2() {
		String reg = "^([0-9]+,[0-9]*)+$";
		String str = "00,2,23,3";
		Pattern pat = Pattern.compile(reg);
		Matcher match = pat.matcher(str);
		System.out.println(match.find());
	}

	public void reg() {
		String reg = "^[0-9]+$";
		String str = "134a3";
		Pattern pat = Pattern.compile(reg);
		Matcher match = pat.matcher(str);
		System.out.println(match.find());

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestAll ta = new TestAll();
		ta.test();
	}

}
