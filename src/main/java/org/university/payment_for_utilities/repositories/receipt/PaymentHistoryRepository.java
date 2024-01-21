package org.university.payment_for_utilities.repositories.receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.receipt.PaymentHistory;
import org.university.payment_for_utilities.domains.receipt.Receipt;

import java.util.Optional;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    Optional<PaymentHistory> findByReceipt(Receipt receipt);
}
