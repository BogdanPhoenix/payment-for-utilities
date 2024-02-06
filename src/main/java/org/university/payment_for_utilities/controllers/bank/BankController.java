package org.university.payment_for_utilities.controllers.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.services.interfaces.bank.BankService;

@RestController
@RequestMapping("/${application.uri.main}/banks")
public class BankController extends BankBaseController<BankRequest> {
    @Autowired
    public BankController(BankService service) {
        super(service);
    }
}
