package org.university.payment_for_utilities.repositories.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.company.CompanyTariff;

@Repository
public interface CompanyTariffRepository extends JpaRepository<CompanyTariff, Long> {
}
