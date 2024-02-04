package org.university.payment_for_utilities.domains.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.domains.receipt.Receipt;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;

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
@Table(name = "contract_entities")
public class ContractEntity extends TableInfo {
    @ManyToOne
    @JoinColumn(name = "id_registered_user", nullable = false)
    @NonNull
    private RegisteredUser registeredUser;

    @ManyToOne
    @JoinColumn(name = "id_company_tariff", nullable = false)
    @NonNull
    private CompanyTariff companyTariff;

    @Column(name = "num_contract", length = 11, nullable = false, unique = true)
    @NonNull
    private String numContract;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "contractEntity", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Receipt> receipts;

    @Override
    public ContractEntityResponse getResponse() {
        var responseBuilder = ContractEntityResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .registeredUser(this.registeredUser.getResponse())
                .companyTariff(this.companyTariff.getResponse())
                .numContract(this.numContract)
                .build();
    }
}
