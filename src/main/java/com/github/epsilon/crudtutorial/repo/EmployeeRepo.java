package com.github.epsilon.crudtutorial.repo;

import com.github.epsilon.crudtutorial.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
