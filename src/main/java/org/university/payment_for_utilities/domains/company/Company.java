package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.CommonInstitutionalData;
import org.university.payment_for_utilities.domains.address.AddressResidence;
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
public class Company extends CommonInstitutionalData {
    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_address", nullable = false, unique = true)
    @NonNull
    private AddressResidence address;

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
    public CompanyResponse getResponse() {
        var responseBuilder = CompanyResponse.builder();
        return super
                .responseCommonInstitutionalDataBuilder(responseBuilder)
                .address(this.address.getResponse())
                .currentAccount(this.currentAccount)
                .build();
    }
}
