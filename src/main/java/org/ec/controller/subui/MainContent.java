package org.ec.controller.subui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.ec.controller.client.request.DepartmentRequestHandler;
import org.ec.controller.client.request.EmployeeRequestHandler;
import org.ec.controller.component.EmployeeGrid;
import org.ec.controller.models.Department;
import org.ec.controller.window.AddEditDepartmentDialog;
import org.ec.controller.window.AddEditEmployeeDialog;
import org.ec.controller.window.AlertDialog;
import org.ec.controller.window.AttendMeetingDialog;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class MainContent extends VerticalLayout {

	private final Label title = new Label();

	private final Label description = new Label();

	private Button btnDelete = new Button();

	private Button btnEdit = new Button();

	private Button btnAttendMeeting = new Button("Attend Meeting");

	private Button btnAdd = new Button("Add Employee");

	private Button btnMultiDelete = new Button("Delete Selected");

	private Department department;

	@Autowired
	protected EmployeeGrid employeeGrid;

	@Autowired
	protected SideBarContent sideBarContent;

	@Autowired
	protected AttendMeetingDialog attendMeetingDialog;

	@Autowired
	DepartmentRequestHandler departmentRequestHandler;

	@Autowired
	EmployeeRequestHandler employeeRequestHandler;

	@PostConstruct
	public void init() {

		setWidth("100%");
		setHeight("100%");

		setSpacing(true);
		setMargin(true);

		addTitle();
		addDescription();
		addComponent(employeeGrid);
		addFooter();

		setExpandRatio(employeeGrid, 1);

	}

	private void addTitle() {

		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setMargin(false);
		headerLayout.setSpacing(true);

		title.setWidth("100%");
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);

		headerLayout.addComponent(title);

		btnDelete.setIcon(VaadinIcons.TRASH);
		btnDelete.addStyleName(ValoTheme.BUTTON_DANGER);
		btnDelete.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnDelete.addClickListener(event -> {
			new AlertDialog("Do you want to delete this department?").setClickCallback(clickEvent -> {

				departmentRequestHandler.delete(MainContent.this.department);

				sideBarContent.departmentGrid.setDepartments(department.getDepartmentId());

				List<Department> departments = sideBarContent.departmentGrid.getDepartments();

				if (departments != null && departments.size() > 0) {

					MainContent.this.department = departments.get(departments.size() - 1);

				} else {

					hideView();

				}

			}).show();
		});

		btnEdit.setIcon(VaadinIcons.EDIT);
		btnEdit.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnEdit.addClickListener(event -> {
			new AddEditDepartmentDialog(department).setClickCallback(department -> {

				department = departmentRequestHandler.update(department);

				MainContent.this.department = department;

				sideBarContent.departmentGrid.setDepartments(department.getDepartmentId());

			}).show();
		});

		btnAttendMeeting.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnAttendMeeting.addClickListener(event -> {
			attendMeetingDialog.setDepartment(MainContent.this.department);
			attendMeetingDialog.show();
		});

		headerLayout.addComponents(btnAttendMeeting, btnEdit, btnDelete);

		headerLayout.setExpandRatio(title, 1);

		addComponent(headerLayout);

	}

	private void addDescription() {

		description.setWidth("100%");
		description.addStyleName(ValoTheme.LABEL_H4);
		description.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		addComponent(description);

	}

	public void updateView(Department department) {
		if (department != null) {
			showView();
			this.title.setValue(department.getName());
			this.description.setValue(department.getDescription());
			employeeGrid.setEmployees(department.getDepartmentId(), department.getEmployees());
			this.department = department;
		} else {
			hideView();
		}
	}

	private void addFooter() {

		btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnAdd.addClickListener(event -> {
			new AddEditEmployeeDialog(null).setClickCallback(employee -> {
				employeeRequestHandler.update(department.getDepartmentId(), employee);
				sideBarContent.departmentGrid.setDepartments(department.getDepartmentId());
			}).show();
		});

		btnMultiDelete.addStyleName(ValoTheme.BUTTON_DANGER);
		btnMultiDelete.addClickListener(event -> {
			employeeGrid.getSelectedEmployees().forEach(employee -> {
				employeeRequestHandler.delete(department.getDepartmentId(), employee);
				sideBarContent.departmentGrid.setDepartments(department.getDepartmentId());
			});
		});

		HorizontalLayout footerLayout = new HorizontalLayout();

		footerLayout.setWidth("100%");

		footerLayout.setMargin(false);

		footerLayout.addComponents(btnMultiDelete, btnAdd);

		footerLayout.setComponentAlignment(btnAdd, Alignment.MIDDLE_RIGHT);

		footerLayout.setComponentAlignment(btnMultiDelete, Alignment.MIDDLE_LEFT);

		footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		addComponent(footerLayout);

	}

	public void hideView() {

		for (int i = 0; i < MainContent.this.getComponentCount(); i++) {
			MainContent.this.getComponent(i).setVisible(false);
		}

	}

	public void showView() {

		for (int i = 0; i < MainContent.this.getComponentCount(); i++) {
			MainContent.this.getComponent(i).setVisible(true);
		}

	}

}
