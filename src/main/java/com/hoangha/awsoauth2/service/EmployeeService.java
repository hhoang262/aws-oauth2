package com.hoangha.awsoauth2.service;

import com.hoangha.awsoauth2.dto.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO getEmployee(String info);

    EmployeeDTO getEmployee(String info, FieldType type);

}
