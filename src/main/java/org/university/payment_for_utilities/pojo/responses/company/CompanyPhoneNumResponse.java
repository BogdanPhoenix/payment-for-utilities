package org.university.payment_for_utilities.pojo.responses.company;

import lombok.Builder;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record CompanyPhoneNumResponse(
        Long id,
        Company company,
        PhoneNum phoneNum
) implements Response {
}
