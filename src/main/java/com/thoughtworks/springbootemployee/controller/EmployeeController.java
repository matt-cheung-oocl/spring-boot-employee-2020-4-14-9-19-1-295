package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees() {
		return employees;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee addNewEmployee(@RequestBody Employee employee) {
		employees.add(employee);
		return employee;
	}

	@PutMapping("/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee updatedEmployee) {
		for (Employee employee : employees) {
			if (employee.getId() == employeeId) {
				employee.setAge(updatedEmployee.getAge());
				employee.setGender(updatedEmployee.getGender());
				employee.setName(updatedEmployee.getName());
			}
		}
		return updatedEmployee;
	}

	@DeleteMapping("/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmployee(@PathVariable Integer employeeId) {
		employees.removeIf(employee -> employee.getId() == employeeId);
	}
}
