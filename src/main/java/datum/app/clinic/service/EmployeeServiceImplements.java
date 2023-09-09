package datum.app.clinic.service;

import datum.app.clinic.controller.ClinicDTO;
import datum.app.clinic.model.Employee;
import datum.app.clinic.model.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplements implements EmployeeService{
    public Employee add(ClinicDTO clinicDTO){
        var employee = Employee.builder()
//                .begin(date)
                .enabled(true)
                .role(EmployeeRole.HEAD)
                .build();
        return employee;
    }
}
