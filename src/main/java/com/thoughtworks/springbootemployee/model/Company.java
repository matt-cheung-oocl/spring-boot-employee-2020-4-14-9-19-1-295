package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer companyId;
	private String companyName;
	private Integer employeesNumber;

	@OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
	private List<Employee> employees;
}