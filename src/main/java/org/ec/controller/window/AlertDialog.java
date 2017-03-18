package org.ec.controller.window;

import org.ec.controller.callback.ButtonClickCallback;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class AlertDialog extends Window{

	private String message;
	
	private ButtonClickCallback<Button.ClickEvent> clickCallback;
	
	public AlertDialog setClickCallback(ButtonClickCallback<Button.ClickEvent> clickCallback) {
		this.clickCallback = clickCallback;
		return this;
	}
	
	public AlertDialog() {
		// TODO Auto-generated constructor stub
	}
	
	public AlertDialog(String message) {

		this.message = message;
		
		setCaption("Warning");
		
		setModal(true);
		
		setResizable(false);
		
		setClosable(true);
		
		setContent(addContent());
		
	}
	
	private VerticalLayout addContent(){
		
		VerticalLayout mainUnvisibleLayout = new VerticalLayout();
		
		mainUnvisibleLayout.addComponent(addVisibleContent());
		
		return mainUnvisibleLayout;
		
	}
	
	private VerticalLayout addVisibleContent(){
		
		VerticalLayout mainVisibleContent = new VerticalLayout();
		
		mainVisibleContent.setSpacing(false);
		
		mainVisibleContent.setMargin(false);
		
		mainVisibleContent.addComponents(new Label(message), addFooter());
		
		return mainVisibleContent;
		
	}
	
	private HorizontalLayout addFooter(){
		
		HorizontalLayout footerLayout = new HorizontalLayout();
		
		footerLayout.setMargin(false);
		footerLayout.setSpacing(true);
		footerLayout.setWidth("100%");
		
		footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addClickListener(event ->{
			close();
		});
		
		Button btnDelete = new Button("Delete");
		btnDelete.addStyleName(ValoTheme.BUTTON_DANGER);
		btnDelete.addClickListener(event -> {
			clickCallback.clicked(event);
			close();
		});
		
		footerLayout.addComponents(new Label(), btnCancel, btnDelete);
		
		footerLayout.setExpandRatio(footerLayout.getComponent(0), 1);
		
		return footerLayout;
		
	}
	
	public void show(){
		
		UI.getCurrent().addWindow(this);
		
	}
	
}
