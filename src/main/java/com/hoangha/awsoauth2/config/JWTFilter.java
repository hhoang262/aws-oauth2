package com.hoangha.awsoauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangha.awsoauth2.dto.UserCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTFilter extends UsernamePasswordAuthenticationFilter {

  // We use auth manager to validate the user credentials
  private AuthenticationManager authManager;
  private final AuthenticationConfig authConfig;
  private final ObjectMapper ob = new ObjectMapper();

  public JWTFilter(AuthenticationManager authManager, AuthenticationConfig authConfig) {
    // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
    // In our case, we use "/auth". So, we need to override the defaults.
    this.authConfig = authConfig;
    this.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher(authConfig.getUri(), "POST"));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    Long now = System.currentTimeMillis();
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        // Convert to list of strings.
        // This is important because it affects the way we get them back in the Gateway.
        .claim("authorities", authResult.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + authConfig.getExpiration() * 1000L))  // in milliseconds
        .signWith(SignatureAlgorithm.HS512, authConfig.getSecret().getBytes())
        .compact();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
//    PrintWriter out = response.getWriter();
//    out.print("{\"access_token\":\"" + token + "\"}");
//    out.flush();
    Map<String, Object> resMap = new HashMap<>();
    resMap.put("access_token", token);
    response.getOutputStream().println(ob.writeValueAsString(resMap));
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    Map<String, Object> data = new HashMap<>();
    data.put("code", HttpStatus.UNAUTHORIZED.value());
    data.put("timestamp", System.currentTimeMillis());
    data.put("exception", failed.getMessage());
    response.getOutputStream().println(ob.writeValueAsString(data));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {

      // 1. Get credentials from request
      UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(),
          UserCredentials.class);

      // 2. Create auth object (contains credentials) which will be used by auth manager
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          creds.getUsername(), creds.getPassword(), Collections.emptyList());

      // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
      return authManager.authenticate(authToken);
    } catch (IOException e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }
}
