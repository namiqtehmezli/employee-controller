package org.ec.controller.component;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.ec.controller.client.request.EmployeeRequestHandler;
import org.ec.controller.models.Employee;
import org.ec.controller.subui.SideBarContent;
import org.ec.controller.window.AddEditEmployeeDialog;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;

@SuppressWarnings("serial")
@SpringUI
@UIScope
public class EmployeeGrid extends Grid<Employee> {

	private Set<Employee> employees = new HashSet<>();

	private Set<Employee> selectedEmployees = new HashSet<>();

	private Integer departmentId;

	@Autowired
	SideBarContent sideBarContent;

	@Autowired
	EmployeeRequestHandler employeeRequestHandler;

	@PostConstruct
	public void init() {

		setWidth("100%");
		setHeight("100%");

		addColumn(Employee::getName).setId("name").setCaption("Name");
		addColumn(Employee::getSurname).setId("surname").setCaption("Surname");
		addColumn(Employee::getSalary).setId("salary").setCaption("Salary");
		addColumn(employee -> "Edit", new ButtonRenderer<>(event -> {
			new AddEditEmployeeDialog(event.getItem()).setClickCallback(employee -> {
				employeeRequestHandler.update(departmentId, employee);
				sideBarContent.departmentGrid.setDepartments(departmentId);
			}).show();
		})).setWidth(90).setCaption("Edit");

		addColumn(employee -> "Delete", new ButtonRenderer<>(event -> {
			employeeRequestHandler.delete(departmentId, event.getItem());
			sideBarContent.departmentGrid.setDepartments(departmentId);
		})).setWidth(100).setCaption("Delete");

		MultiSelectionModel<Employee> selectionModel = (MultiSelectionModel<Employee>) setSelectionMode(
				SelectionMode.MULTI);

		selectionModel.addMultiSelectionListener(event -> {

			selectedEmployees = event.getAllSelectedItems();

		});

		setItems(employees);

		HeaderRow hr = prependHeaderRow();

		hr.join(hr.getCell("name"), hr.getCell("surname"), hr.getCell("salary")).setHtml("<strong>Employees</strong>");
	}

	public void setEmployees(Integer departmentId, Set<Employee> employees) {
		this.departmentId = departmentId;
		if (employees != null) {
			this.employees = employees;
		}
		setItems(this.employees);
	}

	public Set<Employee> getSelectedEmployees() {
		return selectedEmployees;
	}

}
