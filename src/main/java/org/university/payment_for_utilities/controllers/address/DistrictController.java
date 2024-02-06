package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.services.interfaces.address.DistrictService;

@RestController
@RequestMapping("/${application.uri.main}/districts")
public class DistrictController extends AdministrativeUnitBaseController<DistrictRequest> {
    @Autowired
    protected DistrictController(DistrictService service) {
        super(service);
    }
}
