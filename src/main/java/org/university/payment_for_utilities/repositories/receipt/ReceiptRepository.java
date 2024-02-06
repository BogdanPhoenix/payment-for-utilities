package org.university.payment_for_utilities.repositories.receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.domains.user.ContractEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByContractEntityAndBankAndBillMonth(ContractEntity contractEntity, Bank bank, LocalDate billMonth);

    @Query(value = """
        select r from Receipt r inner join ContractEntity c\s
        on r.contractEntity.id = c.id\s
        where c.registeredUser.id = :id
        """)
    List<Receipt> findByUserId(Long id);
}
