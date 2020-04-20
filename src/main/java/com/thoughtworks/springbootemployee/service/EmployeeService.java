package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
		return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
	}

	public Employee getEmployeeById(Integer employeeId) {
		return employeeRepository.findById(employeeId).orElse(null);
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee updateEmployee(Integer employeeId, Employee updatedEmployee) {
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

	public void removeEmployee(Integer employeeId) { employeeRepository.deleteById(employeeId); }
}
