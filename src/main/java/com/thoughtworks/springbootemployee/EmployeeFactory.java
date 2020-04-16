package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFactory {
	public static List<Employee> createTestEmployees() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "xiaoming", 22, "male", 5000));
		employees.add(new Employee(2, "xiaohong", 24, "female", 6000));

		return employees;
	}
}