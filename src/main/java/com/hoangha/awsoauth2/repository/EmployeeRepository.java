package com.hoangha.awsoauth2.repository;

import com.hoangha.awsoauth2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee getEmployeeByEmail(String email);
    Employee getEmployeeByMobilePhone(String email);
    Employee getEmployeeByEmployeeCode(String employeeCode);

}
