package org.sandbox.api;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.sandbox.config.Messages;
import org.sandbox.domain.Contract;
import org.sandbox.domain.ObjectList;
import org.sandbox.service.ContractService;
import org.sandbox.service.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(name = "Contract API", description = Messages.DESCRIPTION_CONTRACT_API)
public class ContractApi extends CommonApi {

    private static final String HAS_ROLE_CONTRACT_VIEW = "hasRole('ROLE_CONTRACT_VIEW')";
    private static final String HAS_ROLE_CONTRACT_SAVE = "hasRole('ROLE_CONTRACT_SAVE')";
    private static final String HAS_ROLE_CONTRACT_DELETE = "hasRole('ROLE_CONTRACT_DELETE')";

    private static final String PATH_CONTRACT = "/contract";
    private static final String PATH_CONTRACT_ID = "/contract/{id}";

    @Autowired
    private ContractService contractService;

    @RequestMapping(value = PATH_CONTRACT, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_CONTRACT_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_CONTRACT_LIST)
    public ObjectList getRequestList(
            @RequestParam(required = false) @ApiQueryParam(name = LIMIT, required = false) Integer limit,
            @RequestParam(required = false) @ApiQueryParam(name = OFFSET, required = false) Integer offset) {

        Search search = new Search();
        search.setOffset(offset);
        search.setLimit(limit);
        return ObjectList.create(contractService.getContractList(search), contractService.getContractCount(search));
    }

    @RequestMapping(value = PATH_CONTRACT_ID, method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_CONTRACT_VIEW)
    @ApiMethod(description = Messages.DESCRIPTION_GET_CONTRACT)
    public Contract getContract(HttpServletResponse response,
                                @PathVariable @ApiPathParam(name = ID) Long id) {

        return contractService.getContract(id);
    }
}
