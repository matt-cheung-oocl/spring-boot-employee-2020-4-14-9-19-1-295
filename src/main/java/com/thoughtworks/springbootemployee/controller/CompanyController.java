package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private List<Company> companies = new ArrayList<Company>();

	@GetMapping
	public List<Company> getCompanies(@RequestParam(required = false) Integer page,
																		@RequestParam(required = false) Integer pageSize) {
		if (page != null && pageSize != null) {
			int firstEmployee = page * pageSize - 1;
			int lastEmployee = page * pageSize - 1 + pageSize;
			return companies.subList(firstEmployee, lastEmployee);
		}
		return companies;
	}

	@GetMapping("/{companyId}")
	public Company getSpecificCompany(@PathVariable int companyId) {
		return companies.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null);
	}

	@GetMapping("/{companyId}/employees")
	public List<Employee> getEmployeesOfSpecificCompany(@PathVariable int companyId) {
		return Objects.requireNonNull(
						companies.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null)).getEmployees();
	}

	@PostMapping()
	public Company addNewCompany(@RequestBody Company company) {
		companies.add(company);
		return company;
	}
}
