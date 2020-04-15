package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {

	private int companyId;
	private String companyName;
	private int employeesNumber;
	private List<Employee> employees;

	public int getCompanyId() { return companyId; }

	public void setCompanyId(int companyId) { this.companyId = companyId; }

	public String getCompanyName() { return companyName; }

	public void setCompanyName(String companyName) { this.companyName = companyName; }

	public int getEmployeesNumber() { return employeesNumber; }

	public void setEmployeesNumber(int employeesNumber) { this.employeesNumber = employeesNumber; }

	public List<Employee> getEmployees() { return employees; }

	public void setEmployees(List<Employee> employees) { this.employees = employees; }
}