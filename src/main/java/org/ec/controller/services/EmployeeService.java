package org.ec.controller.services;

import org.ec.controller.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeService extends JpaRepository<Employee, Integer>{

}
