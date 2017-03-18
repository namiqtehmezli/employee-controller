package org.ec.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.ec.controller.models.Meetings;
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
public class MeetingController {

	@Autowired
	protected MeetingsService meetingsService;
	
	@RequestMapping(path="meetings", method=RequestMethod.GET)
	public List<Meetings> findAll(){
		return meetingsService.findAll();
	}
	
	@RequestMapping(path="meeting", method=RequestMethod.GET)
	public Meetings findOneById(@RequestParam(value="mtgId", required=true) Integer meetingId){
		return meetingsService.findOne(meetingId);
	}
	
	@RequestMapping(path="meeting/add", method=RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Meetings meeting){
		if(meeting == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty Meeting Object Found");
		}
		
		return ResponseEntity.ok(meetingsService.save(meeting));
	}
	
	@RequestMapping(path="meeting/change", method=RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody Meetings meeting){
		
		if(meeting == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty Meeting Object Found");
		}
		
		return ResponseEntity.ok(meetingsService.save(meeting));
		
	}
	
	@RequestMapping(path="meeting/delete", method=RequestMethod.DELETE)
	public ResponseEntity<?> remove(@RequestParam(value="id", required=false) Integer meetingId){
		
		if(meetingId == null){
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not null id allowed here");
			
		}
		
		meetingsService.delete(meetingId);
		
		return ResponseEntity.ok().build();
		
	}
	
}
