package com.rest.demorest.api;

import com.rest.demorest.helper.UploadCSV;
import com.rest.demorest.model.Employee;
import com.rest.demorest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/data")
@RestController
public class EmployeeControler {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeControler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @GetMapping
    public List<Employee> getEmployeeService() {
        return employeeService.getEmployeeService();
    }

    @GetMapping(path = "{id}")
    public Employee getEmployeeByIdService(@PathVariable("id") UUID id) {
        return employeeService.getEmployeeByIdService(id).orElse(null);
    }

    @DeleteMapping(path = "fired/{id}")
    public String deleteByIdService(@PathVariable("id") UUID id) {
        employeeService.deleteByIdService(id);
        return "Deleted";
    }

    @PutMapping(path = "update/{id}")
    public String updateByIdService(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        employeeService.updateByIdService(id, employee);
        return "Updated!";
    }



    @PostMapping("/csv")
    public String addCSVService(@RequestPart(name = "file", required = false) MultipartFile file) throws Exception {
        String message = "";
        System.out.println(file);
        if (file == null) {
           return "NULL, no file";
        }
        try {
               employeeService.addCSVService(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
               return "Uploaded";
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return message;
        }

    }






}
