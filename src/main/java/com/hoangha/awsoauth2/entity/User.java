package com.hoangha.awsoauth2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "USERS")
@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "USERNAME")
  private String username;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "ROLE")
  private String role;
}
