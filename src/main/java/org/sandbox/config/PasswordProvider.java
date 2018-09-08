package org.sandbox.config;

import org.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PasswordProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        setUserDetailsService(userService);
    }
}
