package org.ec.controller.subui;

import javax.annotation.PostConstruct;

import org.ec.controller.callback.GridSelectionCallback;
import org.ec.controller.client.request.DepartmentRequestHandler;
import org.ec.controller.component.DepartmentGrid;
import org.ec.controller.models.Department;
import org.ec.controller.ui.MainUI;
import org.ec.controller.window.AddEditDepartmentDialog;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class SideBarContent extends VerticalLayout {

	private GridSelectionCallback<Department> selectionListener;

	@Autowired
	public DepartmentGrid departmentGrid;
	
	@Autowired
	DepartmentRequestHandler departmentRequestHandler;

	private final Button btnAdd = new Button("Add Department");

	public void setSelectionListener(GridSelectionCallback<Department> selectionListener) {
		this.selectionListener = selectionListener;
	}

	@PostConstruct
	public void init() {

		setWidth("100%");
		setHeight("100%");
		setMargin(false);

		addComponent(departmentGrid);
		addFooter();

		setExpandRatio(departmentGrid, 1);

		departmentGrid.setSelectionMode(SelectionMode.SINGLE);
		departmentGrid.addSelectionListener(event -> {
			if (event.getFirstSelectedItem().isPresent()) {
				selectionListener.setSelection(event.getFirstSelectedItem().get());
			}
		});

		departmentGrid.focus();

	}

	private void addFooter() {

		btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnAdd.addClickListener(event -> {
			new AddEditDepartmentDialog(null).setClickCallback(department -> {

				department = departmentRequestHandler.update(department);

				departmentGrid.setDepartments(department.getDepartmentId());
				
				if(departmentGrid.getDepartments() != null && departmentGrid.getDepartments().size() > 0){
					((MainUI)UI.getCurrent()).updateMainContent(department);
				}

			}).show();
		});

		HorizontalLayout footerLayout = new HorizontalLayout();

		footerLayout.setWidth("100%");

		footerLayout.setMargin(false);

		footerLayout.addComponents(btnAdd);

		footerLayout.setComponentAlignment(btnAdd, Alignment.MIDDLE_RIGHT);

		footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		addComponent(footerLayout);

	}

}
