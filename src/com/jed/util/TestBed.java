package com.jed.util;

public class TestBed {

	public static void main(String[] args){
		double value = 3d;
		test(value);
		System.out.println(value);
	}
	
	public static void test(Double value){
		value = 5d;
	}
	
}
