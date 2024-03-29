package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.bank.BankPhoneNum;
import org.university.payment_for_utilities.domains.company.CompanyPhoneNum;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.user.InfoAboutUser;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;

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
@Table(name = "phone_numbers")
public class PhoneNum extends TableInfo {
    @Column(name = "number", length = 12, nullable = false, unique = true)
    @NonNull
    private String number;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "phoneNum", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private CompanyPhoneNum companyPhone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "phoneNum", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private BankPhoneNum bankPhoneNum;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "phoneNum", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private InfoAboutUser user;

    @Override
    public PhoneNumResponse getResponse() {
        var responseBuilder = PhoneNumResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .number(this.number)
                .build();
    }
}
