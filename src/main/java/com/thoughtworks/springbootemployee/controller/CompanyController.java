package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private List<Company> companyList = new ArrayList<Company>();

	@GetMapping
	public List<Company> getCompanies() {
		return companyList;
	}

	@GetMapping("/{companyId}")
	public Company getSpecificCompany(@PathVariable int companyId) {
		return companyList.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null);
	}

	@GetMapping("/{companyId}/employees")
	public List<Employee> getEmployeesOfSpecificCompany(@PathVariable int companyId) {
		return Objects.requireNonNull(
						companyList.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null)).getEmployees();
	}

}
