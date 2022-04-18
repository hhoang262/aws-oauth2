package com.hoangha.awsoauth2.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AuthenticationConfig {

  @Value("${security.jwt.auth-path}")
  private String Uri;

  @Value("${security.jwt.header}")
  private String header;

  @Value("${security.jwt.grant-type}")
  private String prefix;

  @Value("${security.jwt.expiration}")
  private int expiration;

  @Value("${security.jwt.secret}")
  private String secret;


}
