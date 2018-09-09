package org.sandbox.api;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.sandbox.config.Messages;
import org.sandbox.domain.Client;
import org.sandbox.domain.ObjectList;
import org.sandbox.service.ClientService;
import org.sandbox.service.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(name = "Client API", description = Messages.DESCRIPTION_CLIENT_API)
public class ClientApi extends CommonApi {

    private static final String HAS_ROLE_CLIENT_VIEW = "hasRole('ROLE_CLIENT_VIEW')";
    private static final String HAS_ROLE_CLIENT_SAVE = "hasRole('ROLE_CLIENT_SAVE')";
    private static final String HAS_ROLE_CLIENT_DELETE = "hasRole('ROLE_CLIENT_DELETE')";

    private static final String PATH_CLIENT = "/client";
    private static final String PATH_CLIENT_ID = "/client/{id}";

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = PATH_CLIENT, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_CLIENT_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_CLIENT_LIST)
    public ObjectList getRequestList(
            @RequestParam(required = false) @ApiQueryParam(name = LIMIT, required = false) Integer limit,
            @RequestParam(required = false) @ApiQueryParam(name = OFFSET, required = false) Integer offset) {

        Search search = new Search();
        search.setOffset(offset);
        search.setLimit(limit);
        return ObjectList.create(clientService.getClientList(search), clientService.getClientCount(search));
    }

    @RequestMapping(value = PATH_CLIENT_ID, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_CLIENT_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_CLIENT)
    public Client getClient(HttpServletResponse response,
                            @PathVariable @ApiPathParam(name = ID) Long id) {

        return clientService.getClient(id);
    }
}
