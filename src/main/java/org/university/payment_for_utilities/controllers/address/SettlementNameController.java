package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;

@RestController
@RequestMapping("/${application.uri.main}/names-settlements")
public class SettlementNameController extends AdministrativeUnitBaseController<SettlementNameRequest> {
    @Autowired
    public SettlementNameController(SettlementNameService service) {
        super(service);
    }
}
