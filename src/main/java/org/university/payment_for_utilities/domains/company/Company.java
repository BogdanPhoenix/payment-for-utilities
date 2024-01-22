package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "companies")
public class Company extends TableInfo {
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

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "current_account", length = 14, nullable = false, unique = true)
    @NonNull
    private String currentAccount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<CompanyPhoneNum> phones;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<CompanyTariff> tariffs;

    @Override
    public boolean isEmpty() {
        return address.isEmpty() ||
                name.isBlank() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                currentAccount.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull Company empty(){
        var builder = builder();
        TableInfo.initEmpty(builder);

        return builder
                .address(AddressResidence.empty())
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .currentAccount("")
                .build();
    }
}
