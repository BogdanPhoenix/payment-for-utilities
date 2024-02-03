package org.university.payment_for_utilities.pojo.responses.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BankResponse extends TransliterationResponse {
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
    public static @NonNull BankResponse empty() {
        return TransliterationResponse
                .initEmpty(builder())
                .website(WebsiteResponse.empty())
                .edrpou(EdrpouResponse.empty())
                .mfo("")
                .build();
    }
}
