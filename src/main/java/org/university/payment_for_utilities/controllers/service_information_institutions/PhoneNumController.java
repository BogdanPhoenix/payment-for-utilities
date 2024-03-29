package org.university.payment_for_utilities.controllers.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.PhoneNumRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.PhoneNumService;

import java.util.List;

@RestController
@RequestMapping("/${application.uri.main}/phone-numbers")
public class PhoneNumController extends AdminDeleteController<PhoneNumRequest> {
    @Autowired
    public PhoneNumController(PhoneNumService service) {
        super(service);
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping({"/bank-admin/{id}", "/company-admin/{id}", "/user/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping({"/bank-admin", "/company-admin", "/user"})
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody PhoneNumRequest request){
        return service.addValue(request);
    }
    @PatchMapping({"/bank-admin/{id}", "/company-admin/{id}", "/user/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody PhoneNumRequest request
    ){
        return service.updateValue(id, request);
    }

    @DeleteMapping({"/bank-admin/{id}", "/company-admin/{id}", "/user/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
