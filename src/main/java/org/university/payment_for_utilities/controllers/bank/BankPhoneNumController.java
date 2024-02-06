package org.university.payment_for_utilities.controllers.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.services.interfaces.bank.BankPhoneNumService;

@RestController
@RequestMapping("/${application.uri.main}/bank-phones")
public class BankPhoneNumController extends BankBaseController<BankPhoneNumRequest> {
    @Autowired
    public BankPhoneNumController(BankPhoneNumService service) {
        super(service);
    }
}
