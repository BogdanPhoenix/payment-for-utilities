package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;

@RestController
@RequestMapping("/${application.uri.main}/settlements")
public class SettlementController extends AdministrativeUnitBaseController<SettlementRequest> {
    @Autowired
    public SettlementController(SettlementService service) {
        super(service);
    }
}
