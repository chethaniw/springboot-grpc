package com.example.springbootgrpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class EmployeeServiceTestConfiguration {

    @Bean
    EmployeeRepository employeeRepository(){
        return mock(EmployeeRepository.class);
    }

    @Bean
    EmployeeServiceImpl employeeService(){
        return new EmployeeServiceImpl();
    }
}
