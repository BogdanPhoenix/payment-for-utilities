package org.university.payment_for_utilities.pojo.requests.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.TransliterationRequest;
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
public class BankRequest extends TransliterationRequest {
    private WebsiteResponse website;
    private EdrpouResponse edrpou;
    private String mfo;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BankRequest empty(){
        return TransliterationRequest
                .initEmpty(builder())
                .website(WebsiteResponse.empty())
                .edrpou(EdrpouResponse.empty())
                .mfo("")
                .build();
    }
}
