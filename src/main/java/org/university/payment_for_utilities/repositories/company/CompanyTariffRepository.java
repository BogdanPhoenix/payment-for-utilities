package org.university.payment_for_utilities.repositories.company;

import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;

import java.util.Optional;

@Repository
public interface CompanyTariffRepository extends TableSearcherRepository<CompanyTariff> {
    Optional<CompanyTariff> findByCompanyAndEnName(Company company, String enName);
}
