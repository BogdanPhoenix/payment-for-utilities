package org.university.payment_for_utilities.controllers.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;

@RestController
@RequestMapping("/${application.uri.main}/edrpous")
public class EdrpouController extends ServiceInfoBaseController<EdrpouRequest> {
    @Autowired
    public EdrpouController(EdrpouService service) {
        super(service);
    }
}
