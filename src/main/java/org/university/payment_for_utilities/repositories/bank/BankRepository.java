package org.university.payment_for_utilities.repositories.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.bank.Bank;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByEdrpou(String edrpou);
}
