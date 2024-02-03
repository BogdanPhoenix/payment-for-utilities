package org.university.payment_for_utilities.pojo.requests.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonInstitutionalDataRequest extends TransliterationRequest {
    private EdrpouResponse edrpou;
    private WebsiteResponse website;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                edrpou.isEmpty() ||
                website.isEmpty();
    }

    @Contract("_ -> param1")
    protected static <T extends CommonInstitutionalDataRequestBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        TransliterationRequest
                .initEmpty(builder)
                .edrpou(EdrpouResponse.empty())
                .website(WebsiteResponse.empty());
        return builder;
    }
}
