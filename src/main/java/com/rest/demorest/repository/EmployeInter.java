package com.rest.demorest.repository;

import com.rest.demorest.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeInter {
    int insertEmployee(UUID id, Employee employee);

    default int insertEmployee(Employee employee){
        UUID id = UUID.randomUUID();
        return insertEmployee(id, employee);
    }
    List<Employee> getEmployee();

    int deleteById(UUID id);

    int updateById(UUID id, Employee employee);
    int saveCSV(MultipartFile file);

    Optional<Employee> getEmployeeById(UUID id);
}
