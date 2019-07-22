package com.xiao.musicdown;

import java.util.Map.Entry;
import java.util.Properties;

public class Test {
	
	public static void main(String[] args) {
		try {
			Properties prop = System.getProperties();
			for (Entry<Object, Object> entry : prop.entrySet()) {
				System.out.printf("%s : %s", entry.getKey(), entry.getValue());
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
