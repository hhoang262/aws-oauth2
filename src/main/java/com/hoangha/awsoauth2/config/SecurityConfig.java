package com.hoangha.awsoauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangha.awsoauth2.common.AuthEntryPointJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationConfig authenticationConfig;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthenticationConfig authenticationConfig,
                          UserDetailsService userDetailsService) {
        this.authenticationConfig = authenticationConfig;
        this.userDetailsService = userDetailsService;
    }

    //    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthEntryPointJwt entryPointJwt() {
        return new AuthEntryPointJwt();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((auth) -> auth.anyRequest().authenticated()
                        .antMatchers(HttpMethod.POST, authenticationConfig.getUri()).permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and().csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // non store any session in memory
//                .and()
//                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().authenticationEntryPoint(entryPointJwt())
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, authenticationConfig.getUri()).permitAll()
//                .anyRequest().authenticated();
//
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
