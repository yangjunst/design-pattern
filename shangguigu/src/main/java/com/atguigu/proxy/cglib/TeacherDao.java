package com.atguigu.proxy.cglib;

public class TeacherDao {

	public String teach() {
		System.out.println("...teacher...");
		return "hello";
	}
	public static void show(){
		System.err.println("...show...");
	}
}
