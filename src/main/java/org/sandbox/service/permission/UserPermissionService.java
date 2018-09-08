package org.sandbox.service.permission;

import org.sandbox.domain.ErrorCollector;
import org.sandbox.domain.User;

public interface UserPermissionService {
    ErrorCollector checkSaveUser(User user);
}
