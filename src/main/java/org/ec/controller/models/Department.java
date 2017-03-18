package org.ec.controller.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import javax.persistence.CascadeType;

@Entity
@Table(name="department")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Department {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="department_id")
	private Integer departmentId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(cascade={CascadeType.REMOVE}, orphanRemoval=true)
	private Set<Employee> employees;
	
	
	public Department() {
		// TODO Auto-generated constructor stub
	}


	public Department(String name, String description, Set<Employee> employees) {
		super();
		this.name = name;
		this.description = description;
		this.employees = employees;
	}


	public Integer getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Employee> getEmployees() {
		return employees;
	}


	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee){
		if(this.employees == null){
			this.employees = new HashSet<>();
		}
		this.employees.add(employee);
	}
}
