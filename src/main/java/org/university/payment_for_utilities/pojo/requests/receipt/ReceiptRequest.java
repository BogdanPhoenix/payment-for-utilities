package org.university.payment_for_utilities.pojo.requests.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;

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
    public static @NonNull ReceiptRequest empty() {
        return ReceiptRequest
                .builder()
                .contractEntity(ContractEntityResponse.empty())
                .bank(BankResponse.empty())
                .billMonth(LocalDate.MIN)
                .build();
    }
}
