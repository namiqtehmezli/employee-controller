package org.ec.controller.services;

import org.ec.controller.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentService extends JpaRepository<Department, Integer>{
	
	
	
}
