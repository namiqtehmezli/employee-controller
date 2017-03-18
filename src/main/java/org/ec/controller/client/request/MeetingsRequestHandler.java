package org.ec.controller.client.request;

import java.util.Arrays;
import java.util.List;

import org.ec.controller.ConnectionConfiguration;
import org.ec.controller.models.Meetings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MeetingsRequestHandler {
	
	@Autowired
	ConnectionConfiguration configuration;

	public List<Meetings> get(){
		
		RestTemplate restTemplate = new RestTemplate();
		
		return Arrays.asList(restTemplate.getForObject(configuration.getUrl()+"/meetings", Meetings[].class));
		
	}
	
	public ResponseEntity<?> update(Meetings meetings){
		
		RestTemplate restTemplate = new RestTemplate();
		
		return restTemplate.postForEntity(configuration.getUrl()+"/meeting/change", meetings, Meetings.class);
		
	}
	
	public void delete(Meetings meeting) {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete(configuration.getUrl()+"/meeting/delete?id="+meeting.getMeetingId());

	}
	
	
	
}
