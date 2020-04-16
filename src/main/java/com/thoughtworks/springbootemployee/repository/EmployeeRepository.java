package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

	private static List<Employee> employees = new ArrayList<>();

	public EmployeeRepository() {
		employees.add(new Employee(0, "Xiaoming", 20, "Male", 5000));
		employees.add(new Employee(1, "Xiaohong", 19, "Female", 6000));
		employees.add(new Employee(2, "Xiaozhi", 15, "Male", 7000));
		employees.add(new Employee(3, "Xiaogang", 16, "Male", 8000));
		employees.add(new Employee(4, "Xiaoxia", 15, "Female", 9000));
	}

	public static List<Employee> getEmployees() {
		return employees;
	}

	public List<Employee> findAll() {
		return employees;
	}

	public List<Employee> findByGender(String gender) {
		return employees.stream()
						.filter(employee -> employee.getGender().equals(gender))
						.collect(Collectors.toList());
	}

	public List<Employee> findByPage(int firstEmployee, int lastEmployee) {
		return employees.subList(firstEmployee, lastEmployee);
	}

	public Employee findById(int employeeId) {
		return employees.stream()
						.filter(employee -> employee.getId() == employeeId)
						.findFirst()
						.orElse(null);
	}

	public Employee save(Employee employee) {
		employees.add(employee);
		return employee;
	}

	public Employee update(int employeeId, Employee updatedEmployee) {
		for (Employee employee : employees) {
			if (employee.getId() == employeeId) {
				if (updatedEmployee.getAge() != 0) {
					employee.setAge(updatedEmployee.getAge());
				}
				if (updatedEmployee.getGender() != null) {
					employee.setGender(updatedEmployee.getGender());
				}
				if (updatedEmployee.getName() != null) {
					employee.setName(updatedEmployee.getName());
				}
				if (updatedEmployee.getSalary() != 0) {
					employee.setSalary(updatedEmployee.getSalary());
				}
			}
		}
		return updatedEmployee;
	}

	public void delete(int employeeId) {
		employees.removeIf(employee -> employee.getId() == employeeId);
	}
}
