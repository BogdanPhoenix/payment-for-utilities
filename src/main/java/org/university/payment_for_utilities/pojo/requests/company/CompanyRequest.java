package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyRequest extends TransliterationRequest {
    private AddressResidenceResponse address;
    private EdrpouResponse edrpou;
    private WebsiteResponse website;
    private String currentAccount;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                address.isEmpty() ||
                edrpou.isEmpty() ||
                website.isEmpty() ||
                currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyRequest empty(){
        return TransliterationRequest
                .initEmpty(builder())
                .address(AddressResidenceResponse.empty())
                .website(WebsiteResponse.empty())
                .edrpou(EdrpouResponse.empty())
                .currentAccount("")
                .build();
    }
}
