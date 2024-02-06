package org.university.payment_for_utilities.controllers.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

@RestController
@RequestMapping("/${application.uri.main}/types-settlement")
public class TypeSettlementController extends AdministrativeUnitBaseController<TypeSettlementRequest> {
    @Autowired
    public TypeSettlementController(TypeSettlementService service) {
        super(service);
    }
}
