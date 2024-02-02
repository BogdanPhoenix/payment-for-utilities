package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "companies")
public class Company extends TransliterationProperty {
    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_address", nullable = false, unique = true)
    @NonNull
    private AddressResidence address;

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_edrpou", nullable = false, unique = true)
    @NonNull
    private Edrpou edrpou;

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_website", nullable = false, unique = true)
    @NonNull
    private Website website;

    @Column(name = "current_account", length = 14, nullable = false, unique = true)
    @NonNull
    private String currentAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<CompanyPhoneNum> phones;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<CompanyTariff> tariffs;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                address.isEmpty() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                currentAccount.isBlank();
    }

    @Override
    public Response getResponse() {
        var responseBuilder = CompanyResponse.builder();
        return super
                .responseInit(responseBuilder)
                .address(this.address)
                .edrpou(this.edrpou)
                .website(this.website)
                .currentAccount(this.currentAccount)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull Company empty(){
        return TransliterationProperty
                .initEmpty(builder())
                .address(AddressResidence.empty())
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .currentAccount("")
                .build();
    }
}
