package uz.pdp.appeufloria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@EnableCaching
@SpringBootApplication
public class AppEufloriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppEufloriaApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
