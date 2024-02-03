package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyResponse extends TransliterationResponse {
    private AddressResidenceResponse address;
    private EdrpouResponse edrpou;
    private WebsiteResponse website;
    private String currentAccount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                address.isEmpty() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyResponse empty(){
        return TransliterationResponse
                .initEmpty(builder())
                .address(AddressResidenceResponse.empty())
                .website(WebsiteResponse.empty())
                .edrpou(EdrpouResponse.empty())
                .currentAccount("")
                .build();
    }
}
