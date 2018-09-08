package org.sandbox.service;

import org.sandbox.domain.LoginParameters;
import org.sandbox.domain.User;

public interface AuthenticationService {

    void login(LoginParameters login);

    void login(String token);

    void logout(String token);

    User getCurrentUser();

    void authenticate(User user);
}
