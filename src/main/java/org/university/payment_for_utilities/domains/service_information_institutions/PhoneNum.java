package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.domains.interfaces.TableInfo;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "phone_numbers")
public class PhoneNum implements TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "number", length = 12, nullable = false, unique = true)
    @NonNull
    private String number;

    @Column(name = "current_data")
    private boolean currentData;

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
