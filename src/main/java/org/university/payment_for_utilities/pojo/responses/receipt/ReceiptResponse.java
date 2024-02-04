package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReceiptResponse extends Response {
    private ContractEntityResponse contractEntity;
    private BankResponse bank;
    private LocalDate billMonth;

    @Override
    public boolean isEmpty() {
        return contractEntity.isEmpty() ||
                bank.isEmpty() ||
                billMonth == LocalDate.MIN;
    }

    @Contract(" -> new")
    public static @NonNull ReceiptResponse empty() {
        return Response
                .initEmpty(builder())
                .contractEntity(ContractEntityResponse.empty())
                .bank(BankResponse.empty())
                .billMonth(LocalDate.MIN)
                .build();
    }
}
