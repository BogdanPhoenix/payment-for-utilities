package org.university.payment_for_utilities.controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.services.interfaces.company.TypeOfferService;

@RestController
@RequestMapping("/${application.uri.main}/types-offer")
public class TypeOfferController extends CompanyBaseController<TypeOfferRequest> {
    @Autowired
    public TypeOfferController(TypeOfferService service) {
        super(service);
    }
}
