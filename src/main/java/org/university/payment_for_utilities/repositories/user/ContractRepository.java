package org.university.payment_for_utilities.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.user.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
