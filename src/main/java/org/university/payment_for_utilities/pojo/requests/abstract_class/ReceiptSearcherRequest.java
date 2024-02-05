package org.university.payment_for_utilities.pojo.requests.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ReceiptSearcherRequest extends Request {
    private Long receipt;

    @Override
    public boolean isEmpty() {
        return receipt.equals(Response.EMPTY_PARENT_ENTITY);
    }

    @Contract("_ -> param1")
    protected static <T extends ReceiptSearcherRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        builder.receipt(Response.EMPTY_PARENT_ENTITY);
        return builder;
    }
}
