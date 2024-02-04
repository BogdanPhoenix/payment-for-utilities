package org.university.payment_for_utilities.pojo.responses.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
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
public abstract class CommonInstitutionalDataResponse extends TransliterationResponse {
    private EdrpouResponse edrpou;
    private WebsiteResponse website;

    @Override
    public boolean isEmpty() {
        return edrpou.isEmpty() ||
                website.isEmpty();
    }

    @Contract("_ -> new")
    protected static <T extends CommonInstitutionalDataResponseBuilder<?, ?>> @NonNull T initEmpty(@NonNull T builder) {
        TransliterationResponse.initEmpty(builder)
                .edrpou(EdrpouResponse.empty())
                .website(WebsiteResponse.empty());
        return builder;
    }
}
