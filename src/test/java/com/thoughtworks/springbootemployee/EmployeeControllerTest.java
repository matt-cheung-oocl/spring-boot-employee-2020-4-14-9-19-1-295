package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;

	@Before
	public void setUp() throws Exception {
		RestAssuredMockMvc.standaloneSetup(employeeController);
	}

	@Mock
	EmployeeService mockEmployeeService;

	@Test
	public void shouldFindEmployeeById() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/employees/1");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(1, employee.getId());
		Assert.assertEquals("xiaoming", employee.getName());
	}

	@Test
	public void shouldFindEmployeeByGender() {
		MockMvcResponse response = given()
						.params("gender", "male")
						.contentType(ContentType.JSON)
						.when()
						.get("/employees");

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

		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(1, employees.size());
		Assert.assertEquals("xiaoming", employees.get(0).getName());
	}

	@Test
	public void shouldAddEmployee() {
		Employee newEmployee = new Employee(3, "matt", 10, "male", 10000);

		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.body(newEmployee)
						.when()
						.post("/employees");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		Assert.assertEquals("matt", employee.getName());
	}

	@Test
	public void shouldDeleteEmployee() {

		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.delete("/employees/1");

		Assert.assertEquals(200, response.getStatusCode());
	}
}