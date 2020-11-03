package com.thoughtworks.capability.gtb.entrancequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO GTB-工程实践: * 分包合理
// TODO GTB-知识点: * 使用了三层架构
// TODO GTB-知识点: * 了解下lombok的使用
// TODO GTB-知识点: * 需要加强Restful实践
// TODO GTB-工程实践: * 注意单一职责，group和student应该有单独的Controller和Service
// TODO GTB-完成度: * 四个接口都有了
// TODO GTB-测试: - 有Controller层测试，覆盖了Happy Path
// TODO GTB-工程实践: * 需要加强面向对象的意识
@SpringBootApplication
public class GtbEntranceQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtbEntranceQuizApplication.class, args);
	}

}
