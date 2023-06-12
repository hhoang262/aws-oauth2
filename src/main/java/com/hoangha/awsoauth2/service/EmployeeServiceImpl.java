package com.hoangha.awsoauth2.service;

import com.hoangha.awsoauth2.dto.EmployeeDTO;
import com.hoangha.awsoauth2.entity.Employee;
import com.hoangha.awsoauth2.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO getEmployee(String info) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployee(String info, FieldType type) {
        Employee employee;
        switch (type) {
            case EMAIL:
                employee = employeeRepository.getEmployeeByEmail(info);
                break;
            case EMPLOYEE_CODE:
                employee = employeeRepository.getEmployeeByEmployeeCode(info);
                break;
            default:
                employee = null;
                break;
        }

        assert employee != null;
        return EmployeeDTO.toEmployeeDTO(employee);
    }
}
