package com.example.application.data.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.data.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Optional<Employee> findByNameAndPassword(String name, String Password);
}
