package org.university.payment_for_utilities.pojo.requests.company;

import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;

@Builder
public record CompanyPhoneNumRequest(
        Company company,
        String phoneNum
) implements Request {
    @Override
    public boolean isEmpty() {
        return this.company.isEmpty() ||
                this.phoneNum.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyPhoneNumRequest empty(){
        return CompanyPhoneNumRequest
                .builder()
                .company(Company.empty())
                .phoneNum("")
                .build();
    }
}
