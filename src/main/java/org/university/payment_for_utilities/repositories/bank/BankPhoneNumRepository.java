package org.university.payment_for_utilities.repositories.bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;

@Repository
public interface BankPhoneNumRepository extends JpaRepository<BankPhoneNum, Long> {
}