package com.hoangha.awsoauth2.dto;

import com.hoangha.awsoauth2.entity.Employee;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class EmployeeDTO {


    @Email
    private String email;

    @NotEmpty
    private String employeeCode;
    private String managerCode;

    @NotEmpty
    private String mobilePhone;

    private String address;


    public static EmployeeDTO toEmployeeDTO(Employee emp){
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO.email = emp.getEmail();
        empDTO.employeeCode = emp.getEmployeeCode();
        empDTO.managerCode = emp.getManagerCode();
        empDTO.mobilePhone = emp.getMobilePhone();
        empDTO.address = emp.getAddress();
        return empDTO;
    }
}
