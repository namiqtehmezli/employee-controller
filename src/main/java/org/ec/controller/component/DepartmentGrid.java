package org.ec.controller.component;

import java.util.List;

import javax.annotation.PostConstruct;

import org.ec.controller.client.request.DepartmentRequestHandler;
import org.ec.controller.models.Department;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class DepartmentGrid extends Grid<Department>{
	
	private List<Department> departments;
	
	@Autowired
	DepartmentRequestHandler departmentRequestHandler;
	
	@PostConstruct
	public void init(){
		
		setWidth("100%");
		setHeight("100%");
		setDepartments(0);
		addColumn(Department::getName).setCaption("Departments");
		
	}
	
	public void setDepartments(Integer departmentId){
		departments = departmentRequestHandler.get();
		setItems(departments);
		if(departments != null && departments.size() > 0){
			Department foundDepartment = departments.stream().filter(department -> department.getDepartmentId() == departmentId).findAny().orElse(null);
			select(foundDepartment != null ? foundDepartment : departments.get(0));
		}
	}
	
	public List<Department> getDepartments() {
		return departments;
	}
	
	public Department getDepartmentByIndex(int pos){
		if(departments != null && departments.size() > 0){
			return departments.get(pos);
		}
		
		return null;
	}
	
}
