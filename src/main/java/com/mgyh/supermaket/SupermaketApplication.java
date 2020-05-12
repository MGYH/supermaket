package com.mgyh.supermaket;

import com.mgyh.supermaket.util.BeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.mgyh.supermaket")
public class SupermaketApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =SpringApplication.run(SupermaketApplication.class, args);
		//将run方法的返回值赋值给工具类中的静态变量
		BeanUtil.applicationContext = applicationContext;
	}

}
