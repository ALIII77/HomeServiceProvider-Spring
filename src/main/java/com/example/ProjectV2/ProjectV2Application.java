package com.example.ProjectV2;

import com.example.ProjectV2.entity.Admin;
import com.example.ProjectV2.repository.AdminRepository;
import com.example.ProjectV2.service.AdminService;
import com.example.ProjectV2.service.ExpertService;
import com.example.ProjectV2.service.implementation.AdminServiceImpl;
import jakarta.persistence.EntityManager;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class ProjectV2Application {


    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(ProjectV2Application.class, args);
        run.getBean(EntityManager.class);
        ExpertService expertService =run.getBean(ExpertService.class);
    }

}
