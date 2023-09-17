//package datum.app.clinic.service;
//
//import datum.app.clinic.dto.ScheduleDTO;
//import datum.app.clinic.dto.ScheduleMapper;
//import datum.app.clinic.model.Day;
//import datum.app.clinic.model.Employee;
//import datum.app.clinic.model.repositoy.EmployeeRepository;
//import datum.app.clinic.model.repositoy.ScheduleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class ScheduleServiceImpl implements ScheduleService {
//    private final ScheduleRepository scheduleRepository;
//    private final EmployeeRepository employeeRepository;
//
//    @Override
//    public Object schedule(Long id, List<ScheduleDTO> scheduleDTOs) {
//        Employee employee = employeeRepository.findById(id).orElseThrow();
//        scheduleDTOs.forEach(
//                sch -> employee.getSchedules().add(ScheduleMapper.INSTANCE.convert(sch))
//        );
//        employeeRepository.save(employee);
//        return employee.getSchedules();
//    }
//}
