package org.university.payment_for_utilities.repositories.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.bank.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
