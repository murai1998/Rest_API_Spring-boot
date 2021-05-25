package com.rest.demorest.service;

import com.rest.demorest.model.Employee;
import com.rest.demorest.repository.EmployeInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeInter employeInter;


    @Autowired
    public EmployeeService( @Qualifier("postgres") EmployeInter employeInter){
        this.employeInter = employeInter;
    }
   public int addEmployee(Employee employee){
        return employeInter.insertEmployee(employee);
   }

   public List<Employee> getEmployeeService(){
        return employeInter.getEmployee();
   }

    public Optional<Employee> getEmployeeByIdService(UUID id){
        return employeInter.getEmployeeById(id);
    }

    public int deleteByIdService(UUID id){
        return employeInter.deleteById(id);
    }

    public int updateByIdService(UUID id, Employee employee) {

        return employeInter.updateById(id, employee);
    }


    public int addCSVService(MultipartFile file){
        return employeInter.saveCSV(file);
    }


}
