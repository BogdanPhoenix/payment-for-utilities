package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "phone_numbers")
public class PhoneNum extends TableInfo {
    @Column(name = "number", length = 12, nullable = false, unique = true)
    @NonNull
    private String number;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "phoneNum", cascade={MERGE, REMOVE, REFRESH, DETACH}, orphanRemoval = true)
    private CompanyPhoneNum companyPhone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "phoneNum", cascade={MERGE, REMOVE, REFRESH, DETACH}, orphanRemoval = true)
    private BankPhoneNum bankPhoneNum;

    @Override
    public boolean isEmpty() {
        return number.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull PhoneNum empty() {
        return PhoneNum
                .builder()
                .number("")
                .build();
    }
}
