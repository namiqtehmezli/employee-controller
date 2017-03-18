package org.ec.controller.client.request;

import java.util.Arrays;
import java.util.List;

import org.ec.controller.ConnectionConfiguration;
import org.ec.controller.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DepartmentRequestHandler {
	
	@Autowired
	private ConnectionConfiguration configuration;

	public List<Department> get() {

		RestTemplate restTemplate = new RestTemplate();
		Department[] departmentAry = restTemplate.getForObject(configuration.getUrl()+"/departments", Department[].class);

		return Arrays.asList(departmentAry);

	}

	public Department update(Department department) {

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.postForEntity(configuration.getUrl()+"/department/change", department, Department.class)
				.getBody();

	}

	public void delete(Department department) {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete(configuration.getUrl()+"/department/delete?id="+department.getDepartmentId());

	}

}
