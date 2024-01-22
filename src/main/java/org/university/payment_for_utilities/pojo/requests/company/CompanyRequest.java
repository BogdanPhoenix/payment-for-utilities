package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.address.AddressResidence;
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
public class CompanyRequest extends Request {
    private AddressResidence address;
    private Edrpou edrpou;
    private Website website;
    private String name;
    private String currentAccount;

    @Override
    public boolean isEmpty() {
        return this.address.isEmpty() ||
                this.edrpou.isEmpty() ||
                this.website.isEmpty() ||
                this.name.isBlank() ||
                this.currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull CompanyRequest empty(){
        return CompanyRequest
                .builder()
                .address(AddressResidence.empty())
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .currentAccount("")
                .build();
    }
}
