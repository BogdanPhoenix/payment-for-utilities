package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.company.CompanyPhoneNumResponse;

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
@Table(name = "company_phone_nums")
public class CompanyPhoneNum extends TableInfo {
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    @NonNull
    private Company company;

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_phone_num", nullable = false, unique = true)
    @NonNull
    private PhoneNum phoneNum;

    @Override
    public boolean isEmpty() {
        return company.isEmpty() ||
                phoneNum.isEmpty();
    }

    @Override
    public Response getResponse() {
        var responseBuilder = CompanyPhoneNumResponse.builder();
        return super
                .responseInit(responseBuilder)
                .company(this.company)
                .phoneNum(this.phoneNum)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull CompanyPhoneNum empty(){
        return TableInfo
                .initEmpty(builder())
                .company(Company.empty())
                .phoneNum(PhoneNum.empty())
                .build();
    }
}
