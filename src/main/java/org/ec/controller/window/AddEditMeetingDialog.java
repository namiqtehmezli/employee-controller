package org.ec.controller.window;

import org.ec.controller.callback.ButtonClickCallback;
import org.ec.controller.models.Meetings;
import org.springframework.util.StringUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AddEditMeetingDialog extends Window {

	public static final String INVALID_NAME_MSG = "Invalid name. Name should contain at least 3 characters";

	public static final String INVALID_DESCRIPTION_MSG = "Invalid description. Description should contain at least 10 characters";

	private Meetings meeting;

	private Binder<Meetings> binder = new Binder<>();

	private ButtonClickCallback<Meetings> clickCallback;

	public AddEditMeetingDialog setClickCallback(ButtonClickCallback<Meetings> clickCallback) {
		this.clickCallback = clickCallback;
		return this;
	}

	public AddEditMeetingDialog() {
		// TODO Auto-generated constructor stub
	}

	public AddEditMeetingDialog(Meetings meeting) {

		setCaption("Edit Meeting");

		if (meeting == null) {
			setCaption("Add Meeting");
			meeting = new Meetings();
		}

		this.meeting = meeting;

		binder.setBean(meeting);

		setClosable(true);

		setResizable(false);

		setModal(true);

		setContent(addContent());

	}

	private VerticalLayout addContent() {

		VerticalLayout mainUnvisibleLayout = new VerticalLayout();

		mainUnvisibleLayout.addComponents(addFormLayout(), addFooter());

		return mainUnvisibleLayout;

	}

	private FormLayout addFormLayout() {

		FormLayout formLayout = new FormLayout();

		TextField tfName = new TextField("Name");
		tfName.setRequiredIndicatorVisible(true);
		binder.forField(tfName).withValidator(new Validator<String>() {

			@Override
			public ValidationResult apply(String value, ValueContext context) {
				// TODO Auto-generated method stub

				if (StringUtils.isEmpty(value)) {
					return ValidationResult.error(INVALID_NAME_MSG);
				}

				if (value.length() < 3) {
					return ValidationResult.error(INVALID_NAME_MSG);
				}

				return ValidationResult.ok();
			}
		}).bind(Meetings::getName, Meetings::setName);

		TextArea taDescription = new TextArea("Description");
		taDescription.setRequiredIndicatorVisible(true);
		binder.forField(taDescription).withValidator(new Validator<String>() {

			@Override
			public ValidationResult apply(String value, ValueContext context) {
				// TODO Auto-generated method stub

				if (StringUtils.isEmpty(value)) {
					return ValidationResult.error(INVALID_DESCRIPTION_MSG);
				}

				if (value.length() < 10) {
					return ValidationResult.error(INVALID_DESCRIPTION_MSG);
				}

				return ValidationResult.ok();
			}
		}).bind(Meetings::getDescription, Meetings::setDescription);

		formLayout.addComponents(tfName, taDescription);

		formLayout.setSpacing(true);

		formLayout.setMargin(false);

		return formLayout;

	}

	private HorizontalLayout addFooter() {

		Button btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(event -> {
			binder.validate();
			if (binder.isValid()) {
				clickCallback.clicked(meeting);
				close();
			}
		});

		Button btnCancel = new Button("Cancel");
		btnCancel.addClickListener(event -> {
			this.close();
		});

		HorizontalLayout hLayout = new HorizontalLayout();

		hLayout.setMargin(false);

		hLayout.setSpacing(true);

		hLayout.setWidth("100%");

		hLayout.addComponents(new Label(), btnCancel, btnSave);

		hLayout.setExpandRatio(hLayout.getComponent(0), 1);

		hLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		return hLayout;

	}

	public void show() {

		UI.getCurrent().addWindow(this);

	}

}
