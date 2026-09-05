package com.nexel.socialai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@Slf4j
public class NexelSocialAiApplication {

    public static void main(String[] args) {
        log.info("Starting Nexel Social AI application...");
        SpringApplication.run(NexelSocialAiApplication.class, args);
    }
}
