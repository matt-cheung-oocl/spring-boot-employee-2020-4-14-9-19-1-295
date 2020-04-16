package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CompanyRepository {

	private List<Company> companies = new ArrayList<>();

	public CompanyRepository() {

	}


	public List<Company> findAll() {
		return companies;
	}

	public List<Company> findByPage(int firstCompany, int lastCompany) {
		return companies.subList(firstCompany, lastCompany);
	}

	public Company findById(int companyId) {
		return companies.stream()
						.filter(company -> company.getCompanyId() == companyId)
						.findFirst()
						.orElse(null);
	}

	public List<Employee> findEmployeesById(int companyId) {
		return Objects.requireNonNull(
						companies.stream()
										.filter(company -> company.getCompanyId() == companyId)
										.findFirst()
										.orElse(null)).getEmployees();
	}

	public Company save(Company company) {
		companies.add(company);
		return company;
	}

	public Company update(int companyId, Company updatedCompany) {
		for (Company company : companies) {
			if (company.getCompanyId() == companyId) {
				if (updatedCompany.getCompanyName() != null) {
					company.setCompanyName(updatedCompany.getCompanyName());
				}
				if (updatedCompany.getEmployeesNumber() != 0) {
					company.setEmployeesNumber(updatedCompany.getEmployeesNumber());
				}
				if (updatedCompany.getEmployees() != null) {
					company.setEmployees(updatedCompany.getEmployees());
				}
			}
		}
		return updatedCompany;
	}

	public void delete(int companyId) {
		companies.removeIf(company -> company.getCompanyId() == companyId);
	}
}
