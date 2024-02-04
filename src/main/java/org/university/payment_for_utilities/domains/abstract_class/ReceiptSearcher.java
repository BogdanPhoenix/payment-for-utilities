package org.university.payment_for_utilities.domains.abstract_class;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.pojo.responses.abstract_class.ReceiptSearcherResponse;

@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class ReceiptSearcher extends TableInfo {
    @ManyToOne
    @JoinColumn(name = "id_receipt", nullable = false, unique = true)
    @NonNull
    private Receipt receipt;

    @Contract("_ -> new")
    protected <T extends ReceiptSearcherResponse.ReceiptSearcherResponseBuilder<?, ?>> @NonNull T responseReceiptSearcherBuilder(@NonNull T builder) {
        super.responseBuilder(builder)
                .receipt(this.receipt.getResponse());
        return builder;
    }
}
