package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;

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
@Table(name = "edrpou_codes")
public class Edrpou extends TableInfo {
    @Column(name = "edrpou", length = 8, nullable = false, unique = true)
    @NonNull
    private String value;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "edrpou", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "edrpou", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Bank bank;

    @Override
    public EdrpouResponse getResponse() {
        var responseBuilder = EdrpouResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .value(this.value)
                .build();
    }
}
