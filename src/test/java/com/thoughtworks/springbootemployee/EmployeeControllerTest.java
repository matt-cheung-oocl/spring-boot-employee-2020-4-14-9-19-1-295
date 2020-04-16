package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
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
public class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;

	@Before
	public void setUp() throws Exception {
		RestAssuredMockMvc.standaloneSetup(employeeController);
	}

	@Test
	public void should_find_all_employees() {
		MockMvcResponse response = given()
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

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(5, employees.size());
	}

	@Test
	public void should_find_employee_by_id() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/employees/1");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(1, employee.getId());
		Assert.assertEquals("Xiaohong", employee.getName());
	}

	@Test
	public void should_find_employee_by_gender() {
		MockMvcResponse response = given()
						.params("gender", "Male")
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
		Assert.assertEquals(3, employees.size());
		Assert.assertEquals("Xiaoming", employees.get(0).getName());
	}

	@Test
	public void should_add_new_employee() {
		Employee newEmployee = new Employee(5, "Matt", 10, "Male", 10000);

		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.body(newEmployee)
						.when()
						.post("/employees");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		Assert.assertEquals("Matt", employee.getName());
	}

	@Test
	public void should_delete_employee() {
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.delete("/employees/1");

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void should_update_employee() {
		Employee updatedEmployee = new Employee(0, "Xiaoxiaoming", 10, "Male", 10000);
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.body(updatedEmployee)
						.when()
						.put("/employees/0");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals("Xiaoxiaoming", employee.getName());
	}
}
