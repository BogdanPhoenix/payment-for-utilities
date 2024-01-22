package org.university.payment_for_utilities.pojo.requests.bank;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankRequest extends Request {
    private String name;
    private Website website;
    private Edrpou edrpou;
    private String mfo;

    @Override
    public boolean isEmpty() {
        return this.name.isBlank() ||
                this.website.isEmpty() ||
                this.edrpou.isEmpty() ||
                this.mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull BankRequest empty(){
        return BankRequest
                .builder()
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .mfo("")
                .build();
    }
}
