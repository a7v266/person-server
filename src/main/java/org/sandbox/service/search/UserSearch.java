package org.sandbox.service.search;

import org.sandbox.domain.User;
import org.sandbox.utils.StringUtils;

public class UserSearch extends Search {

    public static final String SORT_USERNAME = User.USER_NAME;

    public void setQuest(String quest) {
        if (StringUtils.isNotEmpty(quest)) {
            addFilter(Filter.likeAnywhere(User.USER_NAME, quest));
        }
    }
}
