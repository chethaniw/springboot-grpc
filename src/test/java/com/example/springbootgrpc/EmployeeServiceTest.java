package com.example.springbootgrpc;

import com.example.springbootgrpc.proto.Employee;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig(classes = {EmployeeServiceTestConfiguration.class})
public class EmployeeServiceTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Test
    void testemployee(){
        Employee.EmpRequest request = Employee.EmpRequest.newBuilder()
                .setEmail("test@wzy")
                .build();
    }
}
