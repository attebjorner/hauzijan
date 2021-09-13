package com.gosha.kalosha.hauzijan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:api.properties")
public class HauzijanApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(HauzijanApplication.class, args);
    }
}
