package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
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
public class EmployeeControllerTest {

	private List<Employee> allEmployees = new ArrayList<>();
	private List<Employee> employeesByGender = new ArrayList<>();
	private List<Employee> employeesByPaging = new ArrayList<>();

	@Mock
	private EmployeeService employeeService;

	@Before
	public void setUp() {
		EmployeeController employeeController = new EmployeeController(employeeService);
		RestAssuredMockMvc.standaloneSetup(employeeController);

		allEmployees.add(new Employee(0, "Xiaoming", 20, "Male", 5000, 0));
		allEmployees.add(new Employee(1, "Xiaohong", 19, "Female", 6000, 0));
		allEmployees.add(new Employee(2, "Xiaozhi", 15, "Male", 7000, 0));
		allEmployees.add(new Employee(3, "Xiaogang", 16, "Male", 8000, 1));
		allEmployees.add(new Employee(4, "Xiaoxia", 15, "Female", 9000, 1));

		employeesByGender.add(new Employee(0, "Xiaoming", 20, "Male", 5000, 0));
		employeesByGender.add(new Employee(2, "Xiaozhi", 15, "Male", 7000, 0));
		employeesByGender.add(new Employee(3, "Xiaogang", 16, "Male", 8000, 1));

		employeesByPaging.add(new Employee(1, "Xiaohong", 19, "Female", 6000, 0));
	}

	@Test
	public void should_find_all_employees() {
		doReturn(allEmployees).when(employeeService).getAllEmployees();
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
		Employee returnEmployee = (new Employee(1, "Xiaohong", 20, "Male", 5000, 0));
		doReturn(returnEmployee).when(employeeService).getEmployeeById(any());
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/employees/1");

		Employee employee = response
						.getBody()
						.as(Employee.class);

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(new Integer(1), employee.getId());
		Assert.assertEquals("Xiaohong", employee.getName());
	}

	@Test
	public void should_find_employee_by_gender() {
		doReturn(employeesByGender).when(employeeService).getEmployeesByGender(any());
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

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertEquals(3, employees.size());
		Assert.assertEquals("Xiaoming", employees.get(0).getName());
	}

	@Test
	public void should_find_employee_with_page_2_and_page_size_1() {
		doReturn(employeesByPaging).when(employeeService).getEmployeesByPage(any(), any());
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.get("/employees?page=2&pageSize=1");

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
		Assert.assertEquals(1, employees.size());
		Assert.assertEquals("Xiaohong", employees.get(0).getName());
	}

	@Test
	public void should_add_new_employee() {
		Employee returnEmployee = (new Employee(5, "Matt", 10, "Male", 10000, 1));
		doReturn(returnEmployee).when(employeeService).createEmployee(any());

		Employee newEmployee = new Employee(5, "Matt", 10, "Male", 10000, 1);

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
		//doReturn(employees).when(employeeService).removeEmployee(any());
		MockMvcResponse response = given()
						.contentType(ContentType.JSON)
						.when()
						.delete("/employees/1");

		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void should_update_employee() {
		Employee returnEmployee = new Employee(0, "Xiaoxiaoming", 10, "Male", 10000, 0);
		doReturn(returnEmployee).when(employeeService).updateEmployee(any(), any());

		Employee updatedEmployee = new Employee(0, "Xiaoxiaoming", 10, "Male", 10000, 0);

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
