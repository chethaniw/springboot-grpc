package com.example.springbootgrpc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig(classes = {EmployeeServiceTestConfiguration.class})
public class EmployeeServiceTest {
}
