package org.university.payment_for_utilities.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.address.Oblast;

@Repository
public interface OblastRepository extends JpaRepository<Oblast, Long> {
}
