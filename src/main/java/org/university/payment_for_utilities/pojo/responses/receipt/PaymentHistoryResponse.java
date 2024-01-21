package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentHistoryResponse extends Response {
    private Receipt receipt;
    private Integer prevValueCounter;
    private Integer currentValueCounter;
    private BigDecimal finalPaymentAmount;
}
