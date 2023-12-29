package org.university.payment_for_utilities.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.address.TypeSettlement;

@Repository
public interface TypeSettlementRepository extends JpaRepository<TypeSettlement, Long> {
}
