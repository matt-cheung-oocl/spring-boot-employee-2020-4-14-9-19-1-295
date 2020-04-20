package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

	private List<Company> allCompanies = new ArrayList<>();
	private List<Company> companiesByPaging = new ArrayList<>();
	private List<Employee> allEmployees = new ArrayList<>();

	@Mock
	private CompanyService companyService;

	@Before
	public void setUp() throws Exception {
		CompanyController companyController = new CompanyController(companyService);
		RestAssuredMockMvc.standaloneSetup(companyController);

		allEmployees.add(new Employee(0, "Xiaoming", 20, "Male", 5000, 0));
		allEmployees.add(new Employee(1, "Xiaohong", 19, "Female", 6000, 0));
		allEmployees.add(new Employee(2, "Xiaozhi", 15, "Male", 7000, 0));
		allEmployees.add(new Employee(3, "Xiaogang", 16, "Male", 8000, 1));
		allEmployees.add(new Employee(4, "Xiaoxia", 15, "Female", 9000, 1));

		allCompanies.add(new Company(0, "OOCL", 5, allEmployees));
		allCompanies.add(new Company(1, "CargoSmart", 5, allEmployees));

		companiesByPaging.add(new Company(1, "CargoSmart", 5, allEmployees));
	}

	@Test
	public void should_find_all_companies() {
		doReturn(allCompanies).when(companyService).getAllCompanies();
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
		doReturn(companiesByPaging).when(companyService).getCompaniesByPage(any(), any());
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
		Company returnCompany = new Company(1, "CargoSmart", 5, allEmployees);
		doReturn(returnCompany).when(companyService).getCompanyById(any());

		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/companies/1");

		Company company = response
						.getBody()
						.as(Company.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(new Integer(1), company.getCompanyId());
		Assert.assertEquals("CargoSmart", company.getCompanyName());
	}

	@Test
	public void should_find_OOCL_employees() {
		doReturn(allEmployees).when(companyService).getEmployeesById(any());
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
		Company returnCompany = new Company(2, "COSCO", 5, allEmployees);
		doReturn(returnCompany).when(companyService).createCompany(any());

		Company newCompany = new Company(2, "COSCO", 5, allEmployees);

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
		Company returnCompany = new Company(0, "OOCLL", 5, allEmployees);
		doReturn(returnCompany).when(companyService).updateCompany(any(), any());

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
