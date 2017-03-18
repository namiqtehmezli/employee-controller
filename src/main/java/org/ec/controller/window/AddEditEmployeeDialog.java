package org.ec.controller.window;

import org.ec.controller.callback.ButtonClickCallback;
import org.ec.controller.models.Employee;
import org.springframework.util.StringUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AddEditEmployeeDialog extends Window {

	public static final String INVALID_NAME_MSG = "Invalid Name. Name should contain at least 3 characters.";

	public static final String INVALID_SURNAME_MSG = "Invalid Surname. Surname should contain at least 3 characters.";

	public static final String INVALID_NUMBER_MSG = "Invalid Number. Float number required.";

	private Employee employee;

	private Binder<Employee> binder = new Binder<>();
	
	private ButtonClickCallback<Employee> clickCallback;
	
	public AddEditEmployeeDialog setClickCallback(ButtonClickCallback<Employee> clickCallback) {
		this.clickCallback = clickCallback;
		return this;
	}

	public AddEditEmployeeDialog() {
		// TODO Auto-generated constructor stub
	}

	public AddEditEmployeeDialog(Employee employee) {

		setCaption("Edit Employee");

		if (employee == null) {
			employee = new Employee();
			setCaption("Add Employee");
		}

		this.employee = employee;

		this.binder.setBean(employee);

		setClosable(true);

		setResizable(false);
		
		setModal(true);

		setContent(addContent());

	}

	private VerticalLayout addContent() {

		VerticalLayout mainInvisibleLay = new VerticalLayout();

		mainInvisibleLay.addComponents(addMainContent(), addFooter());

		return mainInvisibleLay;

	}

	private FormLayout addMainContent() {

		FormLayout formLayout = new FormLayout();

		formLayout.setMargin(false);
		formLayout.setSpacing(true);

		TextField tfName = new TextField("Name");
		tfName.setRequiredIndicatorVisible(true);
		binder.forField(tfName).withValidator(new StringLengthValidator(INVALID_NAME_MSG, 3, 100))
				.bind(Employee::getName, Employee::setName);
		formLayout.addComponent(tfName);

		TextField tfSurname = new TextField("Surname");
		tfSurname.setRequiredIndicatorVisible(true);
		binder.forField(tfSurname).withValidator(new StringLengthValidator(INVALID_SURNAME_MSG, 3, 100))
				.bind(Employee::getSurname, Employee::setSurname);
		formLayout.addComponent(tfSurname);

		TextField tfSalary = new TextField("Salary");
		tfSalary.setRequiredIndicatorVisible(true);
		binder.forField(tfSalary).withValidator(new Validator<String>() {

			@Override
			public ValidationResult apply(String value, ValueContext context) {

				if (StringUtils.isEmpty(value)) {
					return ValidationResult.error(INVALID_NUMBER_MSG);
				}

				try {
					Float.parseFloat(value);
				} catch (Exception e) {
					return ValidationResult.error(INVALID_NUMBER_MSG);
				}

				return ValidationResult.ok();
			}
		}).bind(Employee::getStrSalary, Employee::setStrSalary);
		formLayout.addComponent(tfSalary);

		return formLayout;

	}

	private HorizontalLayout addFooter() {

		Button btnSave = new Button("Save");
		btnSave.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSave.addClickListener(event -> {
			binder.validate();
			if (binder.isValid()) {
				clickCallback.clicked(employee);
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
