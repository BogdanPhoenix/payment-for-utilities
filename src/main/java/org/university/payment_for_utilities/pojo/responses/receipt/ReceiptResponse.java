package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReceiptResponse extends Response {
    private ContractEntity contractEntity;
    private Bank bank;
    private LocalDate billMonth;
}
