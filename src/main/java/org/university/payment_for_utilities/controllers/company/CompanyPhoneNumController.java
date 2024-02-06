package org.university.payment_for_utilities.controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.services.interfaces.company.CompanyPhoneNumService;

@RestController
@RequestMapping("/${application.uri.main}/company-phones")
public class CompanyPhoneNumController extends CompanyBaseController<CompanyPhoneNumRequest> {
    @Autowired
    public CompanyPhoneNumController(CompanyPhoneNumService service) {
        super(service);
    }
}
