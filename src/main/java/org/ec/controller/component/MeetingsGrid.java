package org.ec.controller.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.ec.controller.client.request.MeetingsRequestHandler;
import org.ec.controller.models.Department;
import org.ec.controller.models.Meetings;
import org.ec.controller.window.AddEditMeetingDialog;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class MeetingsGrid extends Grid<Meetings> {

	private Department department;

	private List<Meetings> allMeetings = new ArrayList<>();
	
	@Autowired
	MeetingsRequestHandler meetingsRequestHandler;

	@PostConstruct
	public void init() {

		addColumn(Meetings::getName).setCaption("Name");
		addColumn(Meetings::getDescription).setCaption("Description");
		addColumn(meeting -> "Edit", new ButtonRenderer<>(event -> {
			new AddEditMeetingDialog(event.getItem()).setClickCallback(meeting -> {
				meetingsRequestHandler.update(meeting);
				setItems(department, meetingsRequestHandler.get());
			}).show();
		})).setWidth(90).setCaption("Edit");
		
		addColumn(meeting -> "Delete", new ButtonRenderer<>(event -> {
			Meetings meeting = (Meetings)event.getItem();
			
			meeting.getDepartments().clear();
			
			meetingsRequestHandler.update(meeting);
			
			meetingsRequestHandler.delete(meeting);
			
			setItems(department, meetingsRequestHandler.get());
			
		})).setWidth(100).setCaption("Delete");

		MultiSelectionModel<Meetings> selectionModel = (MultiSelectionModel<Meetings>) setSelectionMode(
				SelectionMode.MULTI);

		selectionModel.addMultiSelectionListener(event -> {
			event.getAddedSelection().forEach(meeting -> {
				Department foundDepartment = meeting.getDepartments().stream()
						.filter(dep -> dep.getDepartmentId() == department.getDepartmentId()).findAny().orElse(null);
				if (foundDepartment == null) {
					meeting.getDepartments().add(department);
				}
			});

			event.getRemovedSelection().forEach(meeting -> {
				Department foundDepartment = meeting.getDepartments().stream()
						.filter(dep -> dep.getDepartmentId() == department.getDepartmentId()).findAny().orElse(null);
				if (foundDepartment != null) {
					meeting.getDepartments().remove(foundDepartment);
				}
			});
		});

		setSizeFull();

	}

	public void setItems(Department dep, List<Meetings> meetings) {

		this.department = dep;

		setItems(meetings);

		this.allMeetings = meetings;

		List<Meetings> attendedMeetings = meetings.stream()
				.filter(meeting -> meeting.getDepartments().stream()
						.filter(department -> department.getDepartmentId() == dep.getDepartmentId()).findAny()
						.orElse(null) != null)
				.collect(Collectors.toList());

		attendedMeetings.forEach(meeting -> {
			select(meeting);
		});

	}

	public List<Meetings> getAllMeetings() {
		return allMeetings;
	}

}
