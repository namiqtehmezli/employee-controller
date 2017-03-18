package org.ec.controller.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="meetings")
public class Meetings {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="meeting_id")
	private Integer meetingId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany
	private List<Department> departments;
	
	
	public Meetings() {
		// TODO Auto-generated constructor stub
	}


	public Meetings(String name, String description, List<Department> departments) {
		super();
		this.name = name;
		this.description = description;
		this.departments = departments;
	}


	public Integer getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
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


	public List<Department> getDepartments() {
		return departments;
	}


	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	public void addDepartment(Department department){
		if(this.departments == null){
			this.departments = new ArrayList<>();
		}
		this.departments.add(department);
	}
	
	public void addDepartments(Department...departments){
		Department[] departmentsArry = departments;
		Arrays.asList(departmentsArry).stream().forEach(department -> {
			addDepartment(department);
		});
	}
	
}
