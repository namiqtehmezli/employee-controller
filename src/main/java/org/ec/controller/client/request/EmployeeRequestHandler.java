package org.ec.controller.client.request;

import org.ec.controller.ConnectionConfiguration;
import org.ec.controller.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeRequestHandler {

	@Autowired
	private ConnectionConfiguration configuration;
	
	public ResponseEntity<?> add(int departmentId, Employee employee) {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForEntity(configuration.getUrl()+"/employee/add?depId=" + departmentId, employee,
				Employee.class);

	}

	public ResponseEntity<?> update(int departmentId, Employee employee) {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForEntity(configuration.getUrl()+"/employee/change?depId=" + departmentId, employee,
				Employee.class);

	}

	public ResponseEntity<?> delete(int departmentId, Employee employee) {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForEntity(configuration.getUrl()+"/employee/delete?depId=" + departmentId, employee,
				String.class);

	}

}
