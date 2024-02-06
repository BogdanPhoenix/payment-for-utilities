package org.university.payment_for_utilities.controllers.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.controllers.AdminDeleteController;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.UnitMeasurementRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.UnitMeasurementService;

import java.util.List;

@RestController
@RequestMapping("/${application.uri.main}/units-measurement")
public class UnitMeasurementController extends AdminDeleteController<UnitMeasurementRequest> {
    @Autowired
    public UnitMeasurementController(UnitMeasurementService service) {
        super(service);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Response> getAll() {
        return service.getAll();
    }

    @GetMapping({"/company-admin/{id}", "/admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response getEntity(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/company-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody UnitMeasurementRequest request) {
        return service.addValue(request);
    }

    @PatchMapping("/company-admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody UnitMeasurementRequest request
    ) {
        return service.updateValue(id, request);
    }

    @DeleteMapping({"/company-admin/{id}", "/admin/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        return service.removeValue(id);
    }
}
