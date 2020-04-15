package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees(@RequestParam(required = false) String gender,
																				@RequestParam(required = false) Integer page,
																				@RequestParam(required = false) Integer pageSize) {
		if (gender != null) {
			return employees.stream()
							.filter(employee -> employee.getGender().equals(gender))
							.collect(Collectors.toList());
		}

		if (page != null && pageSize != null) {
			int firstEmployee = page * pageSize - 1;
			int lastEmployee = page * pageSize - 1 + pageSize;
			return employees.subList(firstEmployee, lastEmployee);
		}
		return employees;
	}

	@GetMapping("/{employeeId}")
	public Employee getSpecificEmployee(@PathVariable int employeeId) {
		return employees.stream()
						.filter(employee -> employee.getId() == employeeId)
						.findFirst()
						.orElse(null);
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
				employee.setSalary(updatedEmployee.getSalary());
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
