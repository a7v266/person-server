package org.sandbox.api;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.sandbox.config.Messages;
import org.sandbox.domain.UserGroup;
import org.sandbox.service.UserGroupService;
import org.sandbox.service.search.UserGroupSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Api(name = "UserGroup API", description = Messages.DESCRIPTION_USER_GROUP_API)
public class UserGroupApi extends CommonApi {

    private static final String HAS_ROLE_USER_GROUP_VIEW = "hasRole('ROLE_USER_GROUP_VIEW')";
    private static final String HAS_ROLE_USER_GROUP_SAVE = "hasRole('ROLE_USER_GROUP_SAVE')";

    private static final String PATH_USER_GROUP = "/user-group";
    private static final String PATH_USER_GROUP_ID = "/user-group/{id}";

    @Autowired
    private UserGroupService userGroupService;

    @RequestMapping(value = PATH_USER_GROUP, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_USER_GROUP_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_USER_LIST)
    public List<UserGroup> getUserGroupList(
            @RequestParam(required = false) @ApiQueryParam(name = LIMIT, required = false) Integer limit,
            @RequestParam(required = false) @ApiQueryParam(name = OFFSET, required = false) Integer offset) {

        UserGroupSearch search = new UserGroupSearch();
        search.setOffset(offset);
        search.setLimit(limit);
        return userGroupService.getUserGroupList(search);
    }

    @RequestMapping(value = PATH_USER_GROUP_ID, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_USER_GROUP_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_USER_GROUP)
    public UserGroup getUserGroup(HttpServletResponse response,
            @PathVariable @ApiPathParam(name = ID) Long id) {

        return userGroupService.getUserGroup(id);
    }
}
