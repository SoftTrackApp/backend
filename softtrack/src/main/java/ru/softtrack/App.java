package ru.softtrack;

import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Bean
    public CommandLineRunner connectToDatabase(DataSource dataSource) {
        return args -> {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection.isValid(0));
        } catch (Exception e) {
            System.out.println(e);
        }
        };
    }
}
