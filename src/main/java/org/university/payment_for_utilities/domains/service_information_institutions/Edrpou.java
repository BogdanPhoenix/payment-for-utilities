package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.company.Company;

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
@Table(name = "edrpou_codes")
public class Edrpou extends TableInfo {
    @Column(name = "edrpou", length = 8, nullable = false, unique = true)
    @NonNull
    private String edrpou;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "edrpou", cascade={MERGE, REMOVE, REFRESH, DETACH})
    private Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "edrpou", cascade={MERGE, REMOVE, REFRESH, DETACH})
    private Bank bank;

    @Override
    public boolean isEmpty() {
        return edrpou.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull Edrpou empty(){
        var builder = builder();
        TableInfo.initEmpty(builder);

        return builder
                .edrpou("")
                .build();
    }
}
