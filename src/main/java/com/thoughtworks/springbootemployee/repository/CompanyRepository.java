package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	List<Employee> findEmployeesById(int companyId);

//	public CompanyRepository() {
//		companies.add(new Company(0, "OOCL", 5, EmployeeRepository.getEmployees()));
//		companies.add(new Company(1, "CargoSmart", 5, EmployeeRepository.getEmployees()));
//	}
}
