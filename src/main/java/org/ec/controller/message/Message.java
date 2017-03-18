package org.ec.controller.message;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

public class Message {

	public static void show(String title, String message, Type type){
		
		Notification notification = new Notification(title, message, type);
		notification.setPosition(Position.TOP_RIGHT);
		notification.show(UI.getCurrent().getPage());
		
	}
	
}
