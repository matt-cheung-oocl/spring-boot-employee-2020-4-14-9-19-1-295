package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Employee getEmployeeById(Integer employeeId) {
		return repository.findById(employeeId);
	}

	public Employee createEmployee(Employee employee) {
		return repository.save(employee);
	}

	public void removeEmployee(Integer employeeId) {
		repository.delete(employeeId);
	}

	public Employee updateEmployee(Integer employeeId, Employee updatedEmployee) {
		return repository.update(employeeId, updatedEmployee);
	}

	public List<Employee> getEmployeesByGender(String gender) {
		return repository.findByGender(gender);
	}

	public List<Employee> getEmployeesByPage(int page, int pageSize) {
		int firstEmployee = page * pageSize - 1;
		int lastEmployee = page * pageSize - 1 + pageSize;
		return repository.findByPage(firstEmployee, lastEmployee);
	}
}
