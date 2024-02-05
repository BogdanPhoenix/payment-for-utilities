package org.university.payment_for_utilities.pojo.requests.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReceiptRequest extends Request {
    private Long contractEntity;
    private Long bank;
    private LocalDate billMonth;

    @Override
    public boolean isEmpty() {
        return contractEntity.equals(Response.EMPTY_PARENT_ENTITY) ||
                bank.equals(Response.EMPTY_PARENT_ENTITY) ||
                billMonth == LocalDate.MIN;
    }

    @Contract(" -> new")
    public static @NonNull ReceiptRequest empty() {
        return ReceiptRequest
                .builder()
                .contractEntity(Response.EMPTY_PARENT_ENTITY)
                .bank(Response.EMPTY_PARENT_ENTITY)
                .billMonth(LocalDate.MIN)
                .build();
    }
}
