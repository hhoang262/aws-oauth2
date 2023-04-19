package com.hoangha.awsoauth2.service;

import com.hoangha.awsoauth2.common.CustomException;

public interface UserService {
    Boolean findUsername(String username) throws CustomException;

}
