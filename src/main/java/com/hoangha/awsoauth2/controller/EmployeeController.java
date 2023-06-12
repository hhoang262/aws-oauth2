package com.hoangha.awsoauth2.controller;

import com.hoangha.awsoauth2.dto.EmployeeDTO;
import com.hoangha.awsoauth2.service.EmployeeService;
import com.hoangha.awsoauth2.service.FieldType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController{

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<EmployeeDTO> getEmployee(@RequestParam String data, @RequestParam FieldType type){
        var employee = employeeService.getEmployee(data, type);
        return ResponseEntity.ok(employee);
    }
}
