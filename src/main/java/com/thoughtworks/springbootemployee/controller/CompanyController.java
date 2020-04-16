package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private List<Company> companies = new ArrayList<Company>();

	@Autowired
	private CompanyService companyService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Company> getCompanies(@RequestParam(required = false) Integer page,
																		@RequestParam(required = false) Integer pageSize) {
		if (page != null && pageSize != null) {
			return companyService.getCompaniesByPage(page, pageSize);
		}
		return companyService.getAllCompanies();
	}

	@GetMapping("/{companyId}")
	@ResponseStatus(HttpStatus.OK)
	public Company getSpecificCompany(@PathVariable int companyId) { return companyService.getCompanyById(companyId);
	}

	@GetMapping("/{companyId}/employees")
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getEmployeesOfSpecificCompany(@PathVariable int companyId) {
		return companyService.getEmployeesById(companyId);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Company addNewCompany(@RequestBody Company company) {
		return companyService.createCompany(company);
	}

	@PutMapping("/{companyId}")
	@ResponseStatus(HttpStatus.OK)
	public Company updateCompany(@PathVariable int companyId, @RequestBody Company updatedCompany) {
		return companyService.updateCompany(companyId, updatedCompany);
	}

	@DeleteMapping("/{companyId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCompany(@PathVariable int companyId) {
		companyService.removeCompany(companyId);
	}
}
