package org.ec.controller.window;

import javax.annotation.PostConstruct;

import org.ec.controller.client.request.MeetingsRequestHandler;
import org.ec.controller.component.MeetingsGrid;
import org.ec.controller.models.Department;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class AttendMeetingDialog extends Window {

	private Department department;

	@Autowired
	protected MeetingsGrid meetingsGrid;
	
	@Autowired
	MeetingsRequestHandler meetingsRequestHandler;

	public void setDepartment(Department department) {
		this.department = department;
	}

	public AttendMeetingDialog() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {

		setCaption("Attend Meeting");

		setClosable(true);

		setResizable(false);

		setModal(true);

		setWidth("700px");

	}

	private VerticalLayout addContent() {

		VerticalLayout mainInvisibleLay = new VerticalLayout();

		mainInvisibleLay.addComponent(addMainContent());

		return mainInvisibleLay;

	}

	private VerticalLayout addMainContent() {

		VerticalLayout mainVisibleLay = new VerticalLayout();

		mainVisibleLay.setMargin(false);
		mainVisibleLay.setSpacing(false);

		meetingsGrid.setItems(department, meetingsRequestHandler.get());

		mainVisibleLay.addComponents(meetingsGrid, addFooter());

		return mainVisibleLay;

	}

	private HorizontalLayout addFooter() {

		Button btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(event -> {

			meetingsGrid.getAllMeetings().forEach(meeting -> {
				meetingsRequestHandler.update(meeting);
			});

			close();

		});

		Button btnCancel = new Button("Cancel");
		btnCancel.addClickListener(event -> {
			this.close();
		});

		Button btnAdd = new Button("Add");
		btnAdd.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnAdd.addClickListener(event -> {
			new AddEditMeetingDialog(null).setClickCallback(meeting -> {
				
				meetingsRequestHandler.update(meeting);
				
				meetingsGrid.setItems(department, meetingsRequestHandler.get());
				
			}).show();
		});

		HorizontalLayout hLayout = new HorizontalLayout();

		hLayout.setMargin(false);

		hLayout.setSpacing(true);

		hLayout.setWidth("100%");

		hLayout.addComponents(btnAdd, new Label(), btnCancel, btnSave);

		hLayout.setComponentAlignment(btnAdd, Alignment.MIDDLE_LEFT);
		hLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_RIGHT);
		hLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_RIGHT);

		hLayout.setExpandRatio(hLayout.getComponent(1), 1);

		hLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		return hLayout;

	}

	public void show() {

		setContent(addContent());

		UI.getCurrent().addWindow(this);

	}

}
