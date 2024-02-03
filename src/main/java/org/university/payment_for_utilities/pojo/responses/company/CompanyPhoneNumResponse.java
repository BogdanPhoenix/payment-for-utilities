package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyPhoneNumResponse extends Response {
    private CompanyResponse company;
    private PhoneNumResponse phoneNum;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull CompanyPhoneNumResponse empty(){
        return Response
                .initEmpty(builder())
                .company(CompanyResponse.empty())
                .phoneNum(PhoneNumResponse.empty())
                .build();
    }
}
