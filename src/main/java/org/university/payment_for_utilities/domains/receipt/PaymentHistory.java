package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.CounterSearcher;
import org.university.payment_for_utilities.pojo.responses.receipt.PaymentHistoryResponse;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "payment_history")
public class PaymentHistory extends CounterSearcher {
    @Column(name = "final_payment_amount", nullable = false)
    @NonNull
    private BigDecimal finalPaymentAmount;

    @Override
    public PaymentHistoryResponse getResponse() {
        var responseBuilder = PaymentHistoryResponse.builder();
        return super
                .responseCounterSearcherBuilder(responseBuilder)
                .finalPaymentAmount(this.finalPaymentAmount)
                .build();
    }
}
