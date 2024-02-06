package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

import java.util.List;

@RestController
@RequestMapping("/${application.uri.main}/addresses")
public class AddressResidenceController extends AdminDeleteController<AddressResidenceRequest> {
    @Autowired
    public AddressResidenceController(AddressResidenceService service) {
        super(service);
    }

    @GetMapping("/admin/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping({"/user/{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping({"/user", "/company-admin"})
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody AddressResidenceRequest request) {
        return service.addValue(request);
    }

    @PatchMapping({"/user/{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody AddressResidenceRequest request
    ) {
        return service.updateValue(id, request);
    }

    @DeleteMapping({"/user/{id}", "/company-admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
