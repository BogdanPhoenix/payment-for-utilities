package org.university.payment_for_utilities.controllers.receipt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.receipt.BlockMeterReadingRequest;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockMeterReadingService;

@Controller
@RequestMapping("/${application.uri.main}/blocks-meter-readings")
public class BlockMeterReadingController extends ReceiptBaseController<BlockMeterReadingRequest> {
    @Autowired
    public BlockMeterReadingController(BlockMeterReadingService service) {
        super(service);
    }
}
