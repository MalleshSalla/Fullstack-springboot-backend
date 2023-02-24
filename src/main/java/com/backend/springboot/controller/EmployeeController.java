package com.backend.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.springboot.exception.ResourceNotFoundException;
import com.backend.springboot.model.Employee;
import com.backend.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	

	//get all employees
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>>  getAllEmployees(){
		return  ResponseEntity.ok(employeeRepository.findAll());
	}
	
	// create employee 
	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		
		return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.CREATED);
	}
	
	
	// get the employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:" +id));
		
		return ResponseEntity.ok(employee);
	}
	
	//update the employee by id
	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:" +id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete the employee by id
	@DeleteMapping("employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(@PathVariable Long id){
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee not exist with id:" +id));
		
		employeeRepository.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("Deleted",Boolean.TRUE);
	
		return ResponseEntity.ok(response);
	}
	
	
	
	
}
