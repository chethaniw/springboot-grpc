package com.example.springbootgrpc.service;
import com.example.springbootgrpc.proto.Employee;
import com.example.springbootgrpc.proto.EmployeeServiceGrpc;
import com.example.springbootgrpc.repository.EmployeeRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void employee(Employee.EmpRequest request, StreamObserver<Employee.EmpResponse> responseObserver) {

        String email = request.getEmail();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            if(email.matches(regex)){
                com.example.springbootgrpc.model.Employee employee = employeeRepository.findByEmail(email);
                if (employee != null){
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
                } else{
                    responseObserver.onError(Status.INTERNAL.withDescription("Employee Not Found").asException());
                }

            }else {
                responseObserver.onError(Status.INTERNAL.withDescription("Invalid Email").asException());

            }
    }
}
