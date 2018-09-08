package org.sandbox.service;

import org.sandbox.config.Token;
import org.sandbox.domain.LoginParameters;
import org.sandbox.domain.User;
import org.sandbox.service.persistence.UserPersistence;
import org.sandbox.service.search.Filter;
import org.sandbox.service.search.Search;
import org.sandbox.utils.DigestUtils;
import org.sandbox.utils.Format;
import org.sandbox.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserPersistence userPersistence;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (ObjectUtils.isNotNull(
                securityContext,
                securityContext.getAuthentication(),
                securityContext.getAuthentication().getPrincipal())) {
            return (User) securityContext.getAuthentication().getPrincipal();
        }
        return null;
    }

    @Override
    public void login(LoginParameters login) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        User user = (User) auth.getPrincipal();
        user.setTokenTime(LocalDateTime.now());
        user.setToken(DigestUtils.createSHA256(String.format("%s:%s", user.getUsername(), user.getTokenTime())));
        SecurityContextHolder.getContext().setAuthentication(auth);
        userPersistence.update(user);
    }

    @Override
    public void login(String token) {
        Authentication authentication = authenticationManager.authenticate(new Token(token));
        User user = (User) authentication.getPrincipal();
        user.setTokenTime(LocalDateTime.now());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userPersistence.update(user);
    }

    @Override
    public void logout(String token) {
        if (token == null || token.isEmpty()) {
            return;
        }
        Search search = new Search();
        search.addFilter(Filter.eq(User.TOKEN, token));
        User user = userPersistence.uniqueResult(search);
        if (user != null) {
            user.setToken(null);
            user.setTokenTime(null);
            userPersistence.update(user);
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    public void authenticate(User user) {
        SecurityContextHolder.getContext().setAuthentication(new Token(user, Format.EMPTY_STRING));
    }
}
