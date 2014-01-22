package com.hmart.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hmart.sample.service.SampleDto;
import com.hmart.sample.service.SampleService;

public class TestDB {
	private static ApplicationContext context = null;

	public static void main(String[] args) {
		try {
			ApplicationContext context = getContext();
			SampleService sampleService = (SampleService)context.getBean("sampleImpl");
			
			List<Object> list = sampleService.getStateList();

			for (Object dto : list) {
				SampleDto temp = (SampleDto)dto;
				System.out.println(temp.getStateID() + "\t" + temp.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static public ApplicationContext getContext(){
		String[] config = new String[]{"com/hmart/config/applicationContext.xml"};
		if(context == null){
			context = new ClassPathXmlApplicationContext(config);
		}
		return context;
	}	
}