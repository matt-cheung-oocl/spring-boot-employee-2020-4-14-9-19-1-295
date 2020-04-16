package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

	@Autowired
	private CompanyController companyController;

	@Before
	public void setUp() throws Exception {
		RestAssuredMockMvc.standaloneSetup(companyController);
	}

	@Test
	public void should_find_all_companies() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/companies");

		List<Company> companies = response
						.getBody()
						.as(
										new TypeRef<List<Company>>() {
											@Override
											public Type getType() {
												return super.getType();
											}
										}
						);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(2, companies.size());
	}

	@Test
	public void should_find_company_with_page_2_and_page_size_1() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/companies?page=2&pageSize=1");

		List<Company> companies = response
						.getBody()
						.as(
										new TypeRef<List<Company>>() {
											@Override
											public Type getType() {
												return super.getType();
											}
										}
						);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(1, companies.size());
		Assert.assertEquals("CargoSmart", companies.get(0).getCompanyName());
	}

	@Test
	public void should_find_company_by_id() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/companies/1");

		Company company = response
						.getBody()
						.as(Company.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(1, company.getCompanyId());
		Assert.assertEquals("CargoSmart", company.getCompanyName());
	}

	@Test
	public void should_find_OOCL_employees() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/companies/0/employees");

		List<Employee> employees = response
						.getBody()
						.as(
										new TypeRef<List<Employee>>() {
											@Override
											public Type getType() {
												return super.getType();
											}
										}
						);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(5, employees.size());
	}

	@Test
	public void should_add_new_company() {
		Company newCompany = new Company(2, "COSCO", 5, EmployeeRepository.getEmployees());

		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.body(newCompany)
						.when()
						.post("/companies");

		Company company = response
						.getBody()
						.as(Company.class);

		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		Assert.assertEquals("COSCO", company.getCompanyName());
	}

	@Test
	public void should_delete_company() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.delete("/companies/1");

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void should_update_company() {
		Company updatedCompany = new Company(0, "OOCLL", 0, null);
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.body(updatedCompany)
						.when()
						.put("/companies/0");

		Company company = response
						.getBody()
						.as(Company.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals("OOCLL", company.getCompanyName());
	}
}
