package org.university.payment_for_utilities.domains.bank;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;

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
@Table(name = "banks")
public class Bank extends TransliterationProperty {
    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_edrpou", nullable = false, unique = true)
    @NonNull
    private Edrpou edrpou;

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_website", nullable = false, unique = true)
    @NonNull
    private Website website;

    @Column(name = "mfo", length = 6, nullable = false, unique = true)
    @NonNull
    private String mfo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bank", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<BankPhoneNum> phones;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "bank", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Receipt> receipts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "banks", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<RegisteredUser> users;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                website.isEmpty() ||
                edrpou.isEmpty() ||
                mfo.isBlank();
    }

    @Override
    public Response getResponse() {
        var responseBuilder = BankResponse.builder();
        return super
                .responseInit(responseBuilder)
                .edrpou(this.edrpou)
                .website(this.website)
                .mfo(this.mfo)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull Bank empty(){
        return TransliterationProperty
                .initEmpty(builder())
                .website(Website.empty())
                .edrpou(Edrpou.empty())
                .mfo("")
                .build();
    }
}
