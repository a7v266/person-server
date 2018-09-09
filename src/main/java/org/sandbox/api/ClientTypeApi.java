package org.sandbox.api;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.sandbox.config.Messages;
import org.sandbox.domain.ClientType;
import org.sandbox.domain.ObjectList;
import org.sandbox.service.search.Search;
import org.sandbox.utils.CollectionUtils;
import org.sandbox.utils.NumberUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(name = "ClientType API", description = Messages.DESCRIPTION_CLIENT_TYPE_API)
public class ClientTypeApi extends CommonApi {

    private static final String HAS_ROLE_CLIENT_VIEW = "hasRole('ROLE_CLIENT_TYPE_VIEW')";

    private static final String PATH_CLIENT = "/client-type";

    @RequestMapping(value = PATH_CLIENT, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_CLIENT_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_CLIENT_LIST)
    public ObjectList getRequestList(
            @RequestParam(required = false) @ApiQueryParam(name = LIMIT, required = false) Integer limit,
            @RequestParam(required = false) @ApiQueryParam(name = OFFSET, required = false) Integer offset) {

        Search search = new Search();
        search.setOffset(offset);
        search.setLimit(limit);
        return ObjectList.create(
                CollectionUtils.createArrayList(ClientType.values()),
                NumberUtils.getLong(ClientType.values().length));
    }
}
