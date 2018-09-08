package org.sandbox.service;

import org.sandbox.domain.User;
import org.sandbox.service.search.Search;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getUserList(Search search);

    User getUser(Long id);

    Long getUserCount(Search search);

    User mergeUser(User user);

    User loadUserByToken(String token);
}
