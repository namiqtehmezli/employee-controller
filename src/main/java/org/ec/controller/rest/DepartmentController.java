package org.ec.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.ec.controller.models.Department;
import org.ec.controller.models.Meetings;
import org.ec.controller.services.DepartmentService;
import org.ec.controller.services.MeetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

	@Autowired
	protected DepartmentService departmentService;
	
	@Autowired
	protected MeetingsService meetingsService;
	
	@RequestMapping(path="departments", method=RequestMethod.GET)
	public List<Department> findAll(){
		return departmentService.findAll();
	}
	
	@RequestMapping(path="department", method=RequestMethod.GET)
	public Department findOneById(@RequestParam(value="depId", required=true) Integer departmentId){
		return departmentService.findOne(departmentId);
	}
	
	@RequestMapping(path="department/add", method=RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Department department){
		if(department == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty Department Object Found");
		}
		
		return ResponseEntity.ok(departmentService.save(department));
	}
	
	@RequestMapping(path="department/change", method=RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody Department department){
		
		if(department == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty Department Object Found");
		}
		
		return ResponseEntity.ok(departmentService.save(department));
		
	}
	
	@RequestMapping(path="department/delete", method=RequestMethod.DELETE)
	public ResponseEntity<?> remove(@RequestParam(value="id", required=true) Integer departmentId){
		
		if(departmentId == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null id not allowed");
		}
		
		List<Meetings> meetings = meetingsService.findAll();
		
		List<Meetings> attendedMeetings = meetings.stream().filter(meeting -> 
			meeting.getDepartments().stream().filter(department -> 
			department.getDepartmentId() == departmentId).findAny().orElse(null) != null).collect(Collectors.toList());
		
		attendedMeetings.stream().forEach(meeting -> {
			meeting.getDepartments().remove(meeting.getDepartments().stream().filter(department -> department.getDepartmentId() == departmentId).findAny().orElse(null));
		});
		
		meetingsService.save(attendedMeetings);
		
		departmentService.delete(departmentId);
		return ResponseEntity.ok().build();
		
	}
	
}
