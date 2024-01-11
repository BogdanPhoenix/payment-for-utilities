package org.university.payment_for_utilities.pojo.responses.company;

import lombok.Builder;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;

@Builder
public record CompanyPhoneNumResponse(
        Long id,
        Company company,
        String phoneNum
) implements Response {
}
