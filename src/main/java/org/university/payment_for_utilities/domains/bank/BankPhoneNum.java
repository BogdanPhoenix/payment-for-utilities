package org.university.payment_for_utilities.domains.bank;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.interfaces.TableInfo;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;

import static jakarta.persistence.CascadeType.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "bank_phone_nums")
public class BankPhoneNum implements TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bank", nullable = false)
    @NonNull
    private Bank bank;

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "id_phone_num", nullable = false, unique = true)
    @NonNull
    private PhoneNum phoneNum;

    @Column(name = "current_data")
    private boolean currentData;

    @Override
    public boolean isEmpty() {
        return bank.isEmpty() ||
                phoneNum.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull BankPhoneNum empty(){
        return BankPhoneNum
                .builder()
                .bank(Bank.empty())
                .phoneNum(PhoneNum.empty())
                .build();
    }
}
