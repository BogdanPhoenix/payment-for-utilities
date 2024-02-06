package org.university.payment_for_utilities.controllers.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.receipt.BlockAccrualAmountRequest;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockAccrualAmountService;

@Controller
@RequestMapping("/${application.uri.main}/blocks-accrual-amount")
public class BlockAccrualAmountController extends ReceiptBaseController<BlockAccrualAmountRequest> {
    @Autowired
    public BlockAccrualAmountController(BlockAccrualAmountService service) {
        super(service);
    }
}
