package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees(@RequestParam(required = false) String gender,
																				@RequestParam(required = false) Integer page,
																				@RequestParam(required = false) Integer pageSize) {
		if (gender != null) {
			return employeeService.getEmployeesByGender(gender);
		}
		if (page != null && pageSize != null) {
			return employeeService.getEmployeesByPage(page, pageSize);
		}
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public Employee getSpecificEmployee(@PathVariable Integer employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee addNewEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}

	@PutMapping("/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee updatedEmployee) {
		return employeeService.updateEmployee(employeeId, updatedEmployee);
	}

	@DeleteMapping("/{employeeId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmployee(@PathVariable Integer employeeId) {
		employeeService.removeEmployee(employeeId);
	}
}
