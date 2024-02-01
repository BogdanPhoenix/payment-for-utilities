package org.university.payment_for_utilities.pojo.requests.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.receipt.Receipt;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ReceiptSearcherRequest extends Request {
    private Receipt receipt;

    @Override
    public boolean isEmpty() {
        return receipt.isEmpty();
    }

    @Contract("_ -> param1")
    protected static <T extends ReceiptSearcherRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.receipt(Receipt.empty());
        return builder;
    }
}
