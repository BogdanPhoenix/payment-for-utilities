package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;

@RestController
@RequestMapping("/${application.uri.main}/oblasts")
public class OblastController extends AdministrativeUnitBaseController<OblastRequest> {
    @Autowired
    public OblastController(OblastService service) {
        super(service);
    }
}
