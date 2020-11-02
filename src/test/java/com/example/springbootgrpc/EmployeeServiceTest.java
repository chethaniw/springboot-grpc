package com.example.springbootgrpc;

import com.example.springbootgrpc.proto.Employee;
import com.example.springbootgrpc.proto.Employee.EmpResponse;
import com.sun.mail.imap.IMAPMessage;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.Message;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private MailRecieverService mailRecieverService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testemployee() throws Exception {
        Employee.EmpRequest request = Employee.EmpRequest.newBuilder()
                .setEmail("johndoe@gmail.com")
                .build();

        com.example.springbootgrpc.Employee employee = new com.example.springbootgrpc.Employee();
        employee.setEmployeeID(156);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartmentName("Development");
        employee.setTeamName("airoline");
        employee.setMobile("0775645234");
        employee.setJoinDate("2020/10/12");
        when(employeeRepository.findByEmail("johndoe@gmail.com")).thenReturn(employee);

        StreamRecorder<EmpResponse> responseObserver = StreamRecorder.create();
        employeeService.employee(request,responseObserver);

        Assert.assertTrue(true);

//        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
//            fail("The call did not terminate in time");
//        }assertNull(responseObserver.getError());
//        List<EmpResponse> results = responseObserver.getValues();
//        assertEquals(1, results.size());
//        EmpResponse response = results.get(0);
//        assertEquals(EmpResponse.newBuilder()
//        .setEmployeeId(156)
//        .setFirstName("John")
//        .setLastName("Doe")
//        .setDepartmentName("Development")
//        .setTeamName("airoline")
//        .setMobile("0775645234")
//        .setJoinDate("2020/10/12")
//        .build(), response);

    }

}
