package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<Employee> getEmployeesByGender(String gender) {
		return employeeRepository.findAllByGender(gender);
	}

	public List<Employee> getEmployeesByPage(int page, int pageSize) {
		return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
	}

	public Optional<Employee> getEmployeeById(int employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
		Employee employee = employeeRepository.findById(employeeId).orElse(null);
		if (employee == null) {
			return null;
		}
		if (updatedEmployee.getAge() != null) {
			employee.setAge(updatedEmployee.getAge());
		}
		if (updatedEmployee.getGender() != null) {
			employee.setGender(updatedEmployee.getGender());
		}
		if (updatedEmployee.getName() != null) {
			employee.setName(updatedEmployee.getName());
		}
		if (updatedEmployee.getSalary() != null) {
			employee.setSalary(updatedEmployee.getSalary());
		}
		return employeeRepository.save(employee);
	}

	public void removeEmployee(int employeeId) { employeeRepository.deleteById(employeeId); }
}
