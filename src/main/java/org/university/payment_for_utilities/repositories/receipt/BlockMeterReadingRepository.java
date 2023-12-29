package org.university.payment_for_utilities.repositories.receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.receipt.BlockMeterReading;

@Repository
public interface BlockMeterReadingRepository extends JpaRepository<BlockMeterReading, Long> {
}
