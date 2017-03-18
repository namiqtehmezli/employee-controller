package org.ec.controller.ui;

import org.ec.controller.models.Department;
import org.ec.controller.subui.MainContent;
import org.ec.controller.subui.SideBarContent;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@SpringUI
@Theme("valo")
public class MainUI extends UI {
	
	private final HorizontalLayout mainLayout = new HorizontalLayout();
	
	@Autowired
	protected SideBarContent sideBarContent;
	
	@Autowired
	protected MainContent mainContent;

	@Override
	protected void init(VaadinRequest request) {

		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		
		mainLayout.addComponent(sideBarContent);
		mainLayout.addComponent(mainContent);
		
		mainLayout.setExpandRatio(sideBarContent, 2);
		mainLayout.setExpandRatio(mainContent, 8);
		
		setContent(mainLayout);

		sideBarContent.setSelectionListener(department -> {
			mainContent.updateView(department);
		});
		
		mainContent.updateView(sideBarContent.departmentGrid.getDepartmentByIndex(0));
	}
	
	public void updateMainContent(Department department){
		mainContent.updateView(department);
	}
}
