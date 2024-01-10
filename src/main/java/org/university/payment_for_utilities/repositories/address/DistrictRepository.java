package org.university.payment_for_utilities.repositories.address;

import org.springframework.stereotype.Repository;
import org.university.payment_for_utilities.domains.address.District;

@Repository
public interface DistrictRepository extends TableSearcherRepository<District> {
}
