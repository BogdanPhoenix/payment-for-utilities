package org.university.payment_for_utilities.controllers.receipt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;

public abstract class ReceiptBaseController<R extends Request> extends AdminDeleteController<R> {
    protected ReceiptBaseController(CrudService service) {
        super(service);
    }

    @GetMapping("/admin")
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
    public Response create(@RequestBody R request) {
        return service.addValue(request);
    }

    @PatchMapping({"/company-admin/{id}", "/admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody R request
    ) {
        return service.updateValue(id, request);
    }
}
