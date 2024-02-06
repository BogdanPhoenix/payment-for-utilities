package org.university.payment_for_utilities.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.receipt.ReceiptRequest;
import org.university.payment_for_utilities.pojo.requests.user.ContractEntityRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.user.ContractEntityService;

import java.util.List;

@RestController
@RequestMapping("/${application.uri.main}/contract-entities")
public class ContractEntityController extends AdminDeleteController<ContractEntityRequest> {
    @Autowired
    public ContractEntityController(ContractEntityService service) { super(service); }

    @GetMapping({"/company-admin", "/admin"})
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/company-admin/system")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody ReceiptRequest request) {
        return service.addValue(request);
    }

    @PatchMapping({"/company-admin/{id}", "/admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody ReceiptRequest request
    ) {
        return service.updateValue(id, request);
    }

    @DeleteMapping({"/company-admin/{id}", "/admin/{id}"})
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
