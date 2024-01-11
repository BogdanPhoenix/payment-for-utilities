package org.university.payment_for_utilities.repositories.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;

import java.util.Optional;

@Repository
public interface CompanyPhoneNumRepository extends JpaRepository<CompanyPhoneNum, Long> {
    Optional<CompanyPhoneNum> findByPhoneNum(String phoneNum);
}
