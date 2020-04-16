package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(int employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void removeEmployee(int employeeId) {
		employeeRepository.delete(employeeId);
	}

	public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
		return employeeRepository.update(employeeId, updatedEmployee);
	}

	public List<Employee> getEmployeesByGender(String gender) {
		return employeeRepository.findByGender(gender);
	}

	public List<Employee> getEmployeesByPage(int page, int pageSize) {
		int firstEmployee = page * pageSize - 1;
		int lastEmployee = page * pageSize - 1 + pageSize;
		return employeeRepository.findByPage(firstEmployee, lastEmployee);
	}
}
