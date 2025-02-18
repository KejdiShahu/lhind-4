package com.lhind.internship.jdbc.main;

import com.lhind.internship.jdbc.model.Employee;
import com.lhind.internship.jdbc.repository.EmployeeRepository;

public class Main {

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        Employee employee = new Employee();
        employee.setEmployeeNumber(12);
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setExtension("test");
        employee.setEmail("test@email.com");
        employee.setOfficeCode("Code");
        employee.setReportsTo(3);
        employee.setJobTitle("Job Title");

        employeeRepository.save(employee);
        employeeRepository.findAll().forEach(System.out::println);
        employeeRepository.delete(1);
        System.out.println(employeeRepository.exists(3));
    }
}
