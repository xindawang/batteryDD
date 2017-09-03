package com.iot.dd;

import com.iot.dd.service.weixin.WeixinInitService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;

@SpringBootApplication
public class BattaryDDApplication {


	public static void main(String[] args) {
		SpringApplication.run(BattaryDDApplication.class, args);
		Timer timer=new Timer();
		timer.schedule(new WeixinInitService(),0,36000000);

	}
}
