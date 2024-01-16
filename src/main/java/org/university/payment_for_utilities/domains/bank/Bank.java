package org.university.payment_for_utilities.domains.bank;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.domains.user.RegisteredUser;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "banks")
public class Bank extends TableInfo {
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

    @Column(name = "mfo", length = 6, nullable = false, unique = true)
    @NonNull
    private String mfo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<BankPhoneNum> phones;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<Receipt> receipts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "banks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private transient List<RegisteredUser> users;

    @Override
    public boolean isEmpty() {
        return name.isBlank() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                mfo.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull Bank empty(){
        return Bank
                .builder()
                .name("")
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .mfo("")
                .build();
    }
}
