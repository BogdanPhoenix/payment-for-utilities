package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyPhoneNumRequest extends Request {
    private CompanyResponse company;
    private PhoneNumResponse phoneNum;

    @Override
    public boolean isEmpty() {
        return this.company.isEmpty() ||
                this.phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull CompanyPhoneNumRequest empty(){
        return CompanyPhoneNumRequest
                .builder()
                .company(CompanyResponse.empty())
                .phoneNum(PhoneNumResponse.empty())
                .build();
    }
}
