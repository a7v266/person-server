package org.sandbox.service;

import org.sandbox.config.Messages;
import org.sandbox.domain.ErrorCollectorException;
import org.sandbox.domain.User;
import org.sandbox.service.permission.UserPermissionService;
import org.sandbox.service.persistence.UserGroupPersistence;
import org.sandbox.service.persistence.UserPersistence;
import org.sandbox.service.search.Filter;
import org.sandbox.service.search.Search;
import org.sandbox.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserPersistence userPersistence;

    @Autowired
    private UserGroupPersistence userGroupPersistence;

    @Autowired
    private UserPermissionService userPermissionService;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserList(Search search) {
        return userPersistence.list(search);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getUserCount(Search search) {
        return userPersistence.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userPersistence.get(id);
    }

    @Override
    public User mergeUser(User user) {

        user.setUserGroup(userGroupPersistence.get(user.getUserGroupId(), id -> {
            throw new ErrorCollectorException(Messages.ERROR_USER_GROUP_NOT_FOUND, id);
        }));

        if (StringUtils.isNotEmpty(user.getRawPassword())) {
            user.setPassword(String.format("{noop}%s", user.getRawPassword()));
            //user.setPassword(String.format("{pbkdf2}%s", new Pbkdf2PasswordEncoder().encode(rawPassword)));
        }

        userPermissionService.checkSaveUser(user).throwException();

        return userPersistence.merge(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Search search = new Search();
        search.addFilter(Filter.eq(User.USER_NAME, username));
        User user = userPersistence.uniqueResult(search);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByToken(String token) {
        Search search = new Search();
        search.addFilter(Filter.eq(User.TOKEN, token));
        User user = userPersistence.uniqueResult(search);
        return user;
    }
}
