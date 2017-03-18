package org.ec.controller.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="employee_id")
	private Integer employeeId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="salary")
	private float salary;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(String name, String surname, float salary) {
		super();
		this.name = name;
		this.surname = surname;
		this.salary = salary;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	
	public String getStrSalary(){
		return String.valueOf(getSalary());
	}
	
	public void setStrSalary(String strSalary){
		this.salary =  Float.parseFloat(strSalary);
	}
	
}
