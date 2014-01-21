package com.hmart.test;

import java.util.ArrayList;

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
			
			ArrayList<SampleDto> list = sampleService.getStateList();

			for (SampleDto dto : list) {
				System.out.println(dto.getStateID() + "\t" + dto.getName());
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