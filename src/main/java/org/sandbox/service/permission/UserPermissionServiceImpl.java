package org.sandbox.service.permission;

import org.sandbox.config.Messages;
import org.sandbox.domain.ErrorCollector;
import org.sandbox.domain.User;
import org.sandbox.validation.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class UserPermissionServiceImpl implements UserPermissionService {

    @Override
    public ErrorCollector checkSaveUser(User user) {
        return ValidationUtils.validate(user, new ErrorCollector(Messages.ERROR_SAVE_USER_FORMAT, user));
    }
}
