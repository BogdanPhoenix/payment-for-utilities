package org.university.payment_for_utilities.pojo.responses.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.receipt.ReceiptResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class ReceiptSearcherResponse extends Response {
    private ReceiptResponse receipt;

    @Override
    public boolean isEmpty() {
        return receipt.isEmpty();
    }

    @Contract("_ -> new")
    protected static <T extends ReceiptSearcherResponseBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.receipt(ReceiptResponse.empty());
        return builder;
    }
}
