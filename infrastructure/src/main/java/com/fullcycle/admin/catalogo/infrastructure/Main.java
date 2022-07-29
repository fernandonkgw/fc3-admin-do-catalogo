package com.fullcycle.admin.catalogo.infrastructure;

import com.fullcycle.admin.catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

//    @Bean
//    @DependsOnDatabaseInitialization
//    ApplicationRunner runner(CreateCategoryUseCase createCategoryUseCase) {
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//
//        final var aCommand =
//                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
//
//        createCategoryUseCase.execute(aCommand);
//
//        return args -> {
//
//        };
//    }
}
