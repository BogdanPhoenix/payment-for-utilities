package org.university.payment_for_utilities.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.CrudService;

public abstract class AdminDeleteController<R extends Request> {
    protected final CrudService service;

    protected AdminDeleteController(CrudService service) {
        this.service = service;
    }

    @DeleteMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@RequestBody R request) {
        return service.removeValue(request);
    }

    @DeleteMapping("/admin/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAll() {
        var countRemoved = service.removeAll();
        var message = String.format("%d entity(s) has been deleted.", countRemoved);

        return ResponseEntity.ok(message);
    }
}
