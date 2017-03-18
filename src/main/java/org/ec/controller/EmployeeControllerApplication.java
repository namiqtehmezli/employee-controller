package org.ec.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ec.controller.models.Department;
import org.ec.controller.models.Employee;
import org.ec.controller.models.Meetings;
import org.ec.controller.services.DepartmentService;
import org.ec.controller.services.EmployeeService;
import org.ec.controller.services.MeetingsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeControllerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner insert(EmployeeService employeeService, DepartmentService departmentService, MeetingsService meetingsService){
		return (args) -> {
			
			//Meetings
			List<Meetings> meetings = setUpMeetings();
		
			//Departments
			List<Department> departments = setUpDepartments();
			
			/**------------------------------Employees-----------------------------------**/
			List<Employee> employeesOfEngineeringDepartment = Stream.of(
					new Employee("Namiq", "Tahmazli", 3500),
					new Employee("Naib", "Tahmazli", 5000),
					new Employee("Anar", "Haqverdiyev", 4000)
			).collect(Collectors.toList());
			
			employeesOfEngineeringDepartment.stream().forEach(employee -> {
				departments.get(0).addEmployee(employeeService.save(employee));
			});
			
			List<Employee> employeesOfEconomyDepartment = Stream.of(
					new Employee("Elif", "Pehlivan", 6000),
					new Employee("Aygun", "Abdullayeva", 3700),
					new Employee("Gunel", "Sebzeliyeva", 6300)
			).collect(Collectors.toList());
			
			employeesOfEconomyDepartment.stream().forEach(employee -> {
				departments.get(1).addEmployee(employeeService.save(employee));
			});
			
			List<Employee> employeesOfScienceDepartment = Stream.of(
					new Employee("Cemil", "Topcubasov", 5000),
					new Employee("Anar", "Ibayev", 9000),
					new Employee("Abdulali", "Aliyev", 8000),
					new Employee("Cavid", "Salimov", 7000)
			).collect(Collectors.toList());
			
			employeesOfScienceDepartment.stream().forEach(employee -> {
				departments.get(2).addEmployee(employeeService.save(employee));
			});
			
			
			departments.set(0, departmentService.save(departments.get(0)));
			departments.set(1, departmentService.save(departments.get(1)));
			departments.set(2, departmentService.save(departments.get(2)));
			
			meetings.get(0).addDepartments(departments.get(0), departments.get(1), departments.get(2));
			meetings.get(1).addDepartments(departments.get(1));
			meetings.get(2).addDepartments(departments.get(0), departments.get(2));
			
			meetingsService.save(meetings);
			
		};
	}
	
	protected List<Meetings> setUpMeetings(){
		
		return Stream.of(			
			//Engineering Consulting
			new Meetings("Engineering Consulting", "The Engineering Consulting", null),
			
			//Economy Consulting
			new Meetings("Economy Consulting", "The Economy Consulting", null),
			
			//Science Consulting
			new Meetings("Science Consulting", "The Science Consulting", null)).collect(Collectors.toList());
		
	}
	
	protected List<Department> setUpDepartments(){
		
		return Stream.of(	
				//Department Of Engineering
				new Department("Department Of Engineering", "The Department of Engineering and Appliend Sciences", null),
				
				//Department Of Economy
				new Department("Department Of Economy", "The Department of Economy", null),
				
				//Department Of Science
				new Department("Department Of Science", "The Department of Science", null)).collect(Collectors.toList());
		
	}
}
