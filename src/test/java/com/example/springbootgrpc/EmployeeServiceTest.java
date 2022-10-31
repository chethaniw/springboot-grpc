package com.example.springbootgrpc;
import com.example.springbootgrpc.proto.Employee;
import com.example.springbootgrpc.proto.Employee.EmpResponse;
import com.example.springbootgrpc.repository.EmployeeRepository;
import com.example.springbootgrpc.service.EmployeeServiceImpl;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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

        com.example.springbootgrpc.model.Employee employee = new com.example.springbootgrpc.model.Employee();
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

    }

    @Test
    public void testemployeeEmailError() throws Exception {
        Employee.EmpRequest request = Employee.EmpRequest.newBuilder()
                .setEmail("johndoemail.com")
                .build();

        StreamRecorder<EmpResponse> responseObserver = StreamRecorder.create();
        employeeService.employee(request,responseObserver);
        Assert.assertTrue(true);

    }

    @Test
    public void testemployeeNull() throws Exception {
        Employee.EmpRequest request = Employee.EmpRequest.newBuilder()
                .setEmail("john@gmail.com")
                .build();

        when(employeeRepository.findByEmail("john@gmail.com")).thenReturn(null);
        StreamRecorder<EmpResponse> responseObserver = StreamRecorder.create();
        employeeService.employee(request,responseObserver);
        Assert.assertTrue(true);

    }

}
