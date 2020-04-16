package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
		int firstCompany = page * pageSize - 1;
		int lastCompany = page * pageSize - 1 + pageSize;
		return companyRepository.findByPage(firstCompany, lastCompany);
	}

	public Company getCompanyById(int companyId) {
		return companyRepository.findById(companyId);
	}

	public List<Employee> getEmployeesById(int companyId) {
		return companyRepository.findEmployeesById(companyId);
	}

	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	public Company updateCompany(int companyId, Company updatedCompany) {
		return companyRepository.update(companyId, updatedCompany);
	}

	public void removeCompany(int companyId) {
		companyRepository.delete(companyId);
	}
}
