package com.bilibili;

import com.bilibili.service.websocket.WebSocketService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.bilibili.dao.mapper")
@EnableAsync
@EnableScheduling
@EnableElasticsearchRepositories(basePackages = "com.bilibili.dao.repository")
public class BilibiliApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(BilibiliApp.class,args);
        WebSocketService.setApplicationContext(app);
    }
}
