package org.sandbox.service;

import org.sandbox.domain.UserGroup;
import org.sandbox.service.search.Search;

import java.util.List;

public interface UserGroupService {

    List<UserGroup> getUserGroupList(Search search);

    Long getUserGroupCount(Search search);

    UserGroup getUserGroup(Long id);

    UserGroup mergeUserGroup(UserGroup userGroup);

}
