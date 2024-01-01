package org.university.payment_for_utilities.repositories.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.address.Oblast;

import java.util.Optional;

@Repository
public interface OblastRepository extends JpaRepository<Oblast, Long> {
    Optional<Oblast> findByEnName(String name);
}
