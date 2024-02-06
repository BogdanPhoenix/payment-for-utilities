package org.university.payment_for_utilities.controllers.service_information_institutions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import java.util.List;

public class ServiceInfoBaseController<R extends Request> extends AdminDeleteController<R> {
    protected ServiceInfoBaseController(CrudService service) {
        super(service);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping({"/admin/{id}", "/bank-admin/{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping({"/admin", "/bank-admin", "/company-admin"})
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody EdrpouRequest request) {
        return service.addValue(request);
    }

    @PatchMapping({"/admin/{id}", "/bank-admin{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody EdrpouRequest request
    ) {
        return service.updateValue(id, request);
    }

    @DeleteMapping({"/admin/{id}", "/bank-admin{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
