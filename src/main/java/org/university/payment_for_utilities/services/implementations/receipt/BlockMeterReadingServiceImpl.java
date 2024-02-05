package org.university.payment_for_utilities.services.implementations.receipt;

import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.receipt.BlockMeterReading;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.BlockMeterReadingRequest;
import org.university.payment_for_utilities.repositories.receipt.BlockMeterReadingRepository;
import org.university.payment_for_utilities.repositories.receipt.ReceiptRepository;
import org.university.payment_for_utilities.services.implementations.auxiliary_services.CounterSearcherService;
import org.university.payment_for_utilities.services.interfaces.receipt.BlockMeterReadingService;

@Service
public class BlockMeterReadingServiceImpl extends CounterSearcherService<BlockMeterReading, BlockMeterReadingRepository> implements BlockMeterReadingService {
    protected BlockMeterReadingServiceImpl(
            BlockMeterReadingRepository repository,
            ReceiptRepository receiptRepository
    ) {
        super(repository, "Blocks meter readings", receiptRepository);
    }

    @Override
    protected BlockMeterReading createEntity(Request request) {
        var blockRequest = (BlockMeterReadingRequest) request;
        var receipt = getReceipt(blockRequest.getReceipt());

        return BlockMeterReading
                .builder()
                .receipt(receipt)
                .prevValueCounter(blockRequest.getPrevValueCounter())
                .currentValueCounter(blockRequest.getCurrentValueCounter())
                .build();
    }
}
