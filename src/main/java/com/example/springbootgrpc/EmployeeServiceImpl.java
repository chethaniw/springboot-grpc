package com.example.springbootgrpc;

import com.example.springbootgrpc.EmployeeRepository;
import com.example.springbootgrpc.proto.Employee;
import com.example.springbootgrpc.proto.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void employee(Employee.EmpRequest request, StreamObserver<Employee.EmpResponse> responseObserver) {
//        super.employee(request, responseObserver);

        com.example.springbootgrpc.Employee employee = employeeRepository.findByEmail(request.getEmail());
        Employee.EmpResponse empResponse = Employee.EmpResponse.newBuilder()
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setDepartmentName(employee.getDepartmentName())
                .setTeamName(employee.getTeamName())
                .setJoinDate(employee.getJoinDate())
                .setMobile(employee.getMobile())
                .setEmployeeId(employee.getEmployeeID()).build();

        responseObserver.onNext(empResponse);
        responseObserver.onCompleted();
    }
}
