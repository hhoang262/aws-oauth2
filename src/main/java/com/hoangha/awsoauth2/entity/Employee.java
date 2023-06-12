package com.hoangha.awsoauth2.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_code", unique = true)
    private String employeeCode;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private OffsetDateTime dayOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "email", unique = true)
    private String email;


    @Column(name = "manager_code")
    private String managerCode;

    @Column(name = "created_on", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdOn;

    @Column(name = "last_updated_on", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime lastUpdatedOn;

}
