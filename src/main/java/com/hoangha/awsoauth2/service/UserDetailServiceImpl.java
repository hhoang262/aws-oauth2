package com.hoangha.awsoauth2.service;

import com.hoangha.awsoauth2.entity.User;
import com.hoangha.awsoauth2.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());

    // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
    // And used by auth manager to verify and check user authentication.
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), grantedAuthorities);
  }
}
