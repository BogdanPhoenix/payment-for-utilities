package org.university.payment_for_utilities.controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.company.CompanyTariffRequest;
import org.university.payment_for_utilities.services.interfaces.company.CompanyTariffService;

@RestController
@RequestMapping("/${application.uri.main}/company-tariffs")
public class CompanyTariffController extends CompanyBaseController<CompanyTariffRequest> {
    @Autowired
    public CompanyTariffController(CompanyTariffService service) {
        super(service);
    }
}
