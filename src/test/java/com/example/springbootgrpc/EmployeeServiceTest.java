package com.example.springbootgrpc;

import com.example.springbootgrpc.proto.Employee;
import com.example.springbootgrpc.proto.Employee.EmpResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.yaml.snakeyaml.reader.StreamReader;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.jsoup.helper.Validate.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@SpringJUnitConfig(classes = {EmployeeServiceTestConfiguration.class})
public class EmployeeServiceTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Test
    void testemployee() throws Exception {
        Employee.EmpRequest request = Employee.EmpRequest.newBuilder()
                .setEmail("test@wzy")
                .build();

        StreamRecorder<EmpResponse> responseObserver = StreamRecorder.create();
        employeeService.employee(request,responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }assertNull(responseObserver.getError());
        List<EmpResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());
        EmpResponse response = results.get(0);
        assertEquals(EmpResponse.newBuilder()
        .setEmployeeId(002)
        .setFirstName("John")
        .setLastName("Doe")
        .setDepartmentName("Dev")
        .setTeamName("teamairmart")
        .setMobile("0774051942")
        .setJoinDate("2020/10/12")
        .build(), response);
    }
}
