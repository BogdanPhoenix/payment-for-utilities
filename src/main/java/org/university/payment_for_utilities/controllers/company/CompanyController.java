package org.university.payment_for_utilities.controllers.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.services.interfaces.company.CompanyService;

@RestController
@RequestMapping("/${application.uri.main}/companies")
public class CompanyController extends CompanyBaseController<CompanyRequest> {
    @Autowired
    public CompanyController(CompanyService service) {
        super(service);
    }
}
