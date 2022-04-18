package com.hoangha.awsoauth2.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationConfig authenticationConfig;
  private final UserDetailsService userDetailsService;

  public SecurityConfig(AuthenticationConfig authenticationConfig,
      UserDetailsService userDetailsService) {
    this.authenticationConfig = authenticationConfig;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // non store any session in memory
        .and()
        .addFilter(new JWTFilter(authenticationManager(),
            authenticationConfig)) // add JWT Filter here when request to another service.
        .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(
            HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, authenticationConfig.getUri()).permitAll()
        .anyRequest().authenticated();

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
