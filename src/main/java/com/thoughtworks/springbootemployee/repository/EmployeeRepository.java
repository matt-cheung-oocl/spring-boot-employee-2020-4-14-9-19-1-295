package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

	private List<Employee> employees = new ArrayList<>();

	public EmployeeRepository() {
		employees.add(new Employee(1, "xiaoming", 22, "male", 5000));
		employees.add(new Employee(2, "xiaohong", 24, "female", 6000));
	}

	public List<Employee> findAll() {
		return employees;
	}

	public Employee findById(Integer employeeId) {
		return employees.stream()
						.filter(employee -> employee.getId() == employeeId)
						.findFirst()
						.orElse(null);
	}

	public Employee save(Employee employee) {
		employees.add(employee);
		return employee;
	}

	public void delete(Integer employeeId) {
		employees.removeIf(employee -> employee.getId() == employeeId);
	}

	public Employee update(Integer employeeId, Employee updatedEmployee) {
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

	public List<Employee> findByGender(String gender) {
		return employees.stream()
						.filter(employee -> employee.getGender().equals(gender))
						.collect(Collectors.toList());
	}

	public List<Employee> findByPage(int firstEmployee, int lastEmployee) {
		return employees.subList(firstEmployee, lastEmployee);
	}
}
