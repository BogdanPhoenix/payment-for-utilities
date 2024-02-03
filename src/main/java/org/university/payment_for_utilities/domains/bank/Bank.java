package org.university.payment_for_utilities.domains.bank;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.CommonInstitutionalData;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
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
public class Bank extends CommonInstitutionalData {
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
    public BankResponse getResponse() {
        var responseBuilder = BankResponse.builder();
        return super
                .responseCommonInstitutionalDataBuilder(responseBuilder)
                .mfo(this.mfo)
                .build();
    }
}
