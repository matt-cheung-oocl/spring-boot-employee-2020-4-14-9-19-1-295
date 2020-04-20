package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public List<Company> getCompaniesByPage(Integer page, Integer pageSize) {
		return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
	}

	public Company getCompanyById(Integer companyId) {
		return companyRepository.findById(companyId).orElse(null);
	}

	public List<Employee> getEmployeesById(Integer companyId) {
		Company company = companyRepository.findById(companyId).orElse(null);
		if (company != null) {
			return company.getEmployees();
		}
		return null;
	}

	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	public Company updateCompany(Integer companyId, Company updatedCompany) {
		Company company = companyRepository.findById(companyId).orElse(null);
		if (company == null) {
			return null;
		}
		if (updatedCompany.getCompanyName() != null) {
			company.setCompanyName(updatedCompany.getCompanyName());
		}
		if (updatedCompany.getEmployeesNumber() != null) {
			company.setEmployeesNumber(updatedCompany.getEmployeesNumber());
		}
		if (updatedCompany.getEmployees() != null) {
			company.setEmployees(updatedCompany.getEmployees());
		}
		return companyRepository.save(company);
	}

	public void removeCompany(Integer companyId) {
		companyRepository.deleteById(companyId);
	}
}
