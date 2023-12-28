package org.university.payment_for_utilities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.AddressResidence;

@Repository
public interface AddressResidenceRepository extends JpaRepository<AddressResidence, Long> {
}
