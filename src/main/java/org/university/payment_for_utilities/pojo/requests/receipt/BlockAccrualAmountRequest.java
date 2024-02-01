package org.university.payment_for_utilities.pojo.requests.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.ReceiptSearcherRequest;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BlockAccrualAmountRequest extends ReceiptSearcherRequest {
    private String debtBeginMonth;
    private String debtEndMonth;
    private String fine;
    private String lastCreditedPayment;
    private String amountDue;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                debtBeginMonth.isBlank()||
                debtEndMonth.isBlank()||
                fine.isBlank()||
                lastCreditedPayment.isBlank()||
                amountDue.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BlockAccrualAmountRequest empty() {
        return ReceiptSearcherRequest
                .initEmpty(builder())
                .debtBeginMonth("")
                .debtEndMonth("")
                .fine("")
                .lastCreditedPayment("")
                .amountDue("")
                .build();
    }
}
