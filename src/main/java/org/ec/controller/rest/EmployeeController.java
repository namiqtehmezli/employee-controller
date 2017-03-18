package org.ec.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.ec.controller.models.Department;
import org.ec.controller.models.Employee;
import org.ec.controller.services.DepartmentService;
import org.ec.controller.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	protected EmployeeService employeeService;

	@Autowired
	protected DepartmentService departmentService;

	@RequestMapping(path = "employees", method = RequestMethod.GET)
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	@RequestMapping(path = "employee", method = RequestMethod.GET)
	public Employee findOneById(@RequestParam(value = "id") Integer id) {
		return employeeService.findOne(id);
	}

	@RequestMapping(path = "employee/add", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestParam(value = "depId", required = true) Integer departmentId,
			@Valid @RequestBody Employee employee) {

		if (departmentId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null Department Id");
		}

		Department department = departmentService.findOne(departmentId);

		if (department == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
		}

		Employee createdEmployee = employeeService.save(employee);

		department.addEmployee(createdEmployee);

		departmentService.save(department);

		return ResponseEntity.ok(createdEmployee);
	}

	@RequestMapping(path = "employee/change", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestParam(value = "depId", required = true) Integer departmentId,
			@Valid @RequestBody Employee employee) {

		if (departmentId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null Department Id");
		}

		if (employee == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty Employee Object Found");
		}

		Department department = departmentService.findOne(departmentId);

		if (department == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
		}

		Employee updatedEmployee = employeeService.save(employee);

		department.addEmployee(updatedEmployee);

		departmentService.save(department);

		return ResponseEntity.ok(updatedEmployee);

	}

	@RequestMapping(path = "employee/delete", method = RequestMethod.POST)
	public ResponseEntity<?> remove(@RequestParam(value = "depId", required = true) Integer departmentId,
			@Valid @RequestBody Employee employee) {

		if (departmentId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null Department Id");
		}

		if (employee == null || employee.getEmployeeId() == null || employee.getEmployeeId() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Could not be determined");
		}

		Department department = departmentService.findOne(departmentId);

		Employee foundEmployee = department.getEmployees().stream()
				.filter(emp -> emp.getEmployeeId() == employee.getEmployeeId()).findAny().orElse(null);

		if (foundEmployee != null) {
			department.getEmployees().remove(foundEmployee);
		}
		
		departmentService.save(department);

		employeeService.delete(employee);

		return ResponseEntity.ok().build();

	}

}
