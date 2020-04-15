package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private List<Company> companies = new ArrayList<Company>();

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
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
	@ResponseStatus(HttpStatus.OK)
	public Company getSpecificCompany(@PathVariable int companyId) {
		return companies.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null);
	}

	@GetMapping("/{companyId}/employees")
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getEmployeesOfSpecificCompany(@PathVariable int companyId) {
		return Objects.requireNonNull(
						companies.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null)).getEmployees();
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Company addNewCompany(@RequestBody Company company) {
		companies.add(company);
		return company;
	}

	@PutMapping("/{companyId}")
	@ResponseStatus(HttpStatus.OK)
	public Company updateCompany(@PathVariable Integer companyId, @RequestBody Company updatedCompany) {
		for (Company company : companies) {
			if (company.getCompanyId() == companyId) {
				company.setCompanyName(updatedCompany.getCompanyName());
				company.setEmployeesNumber(updatedCompany.getEmployeesNumber());
				company.setEmployees(updatedCompany.getEmployees());
			}
		}
		return updatedCompany;
	}

	@DeleteMapping("/{companyId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCompany(@PathVariable int companyId) {
		companies.removeIf(company -> company.getCompanyId() == companyId);
	}
}
