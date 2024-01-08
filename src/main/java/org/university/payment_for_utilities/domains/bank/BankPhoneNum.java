package org.university.payment_for_utilities.domains.bank;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.TableInfo;

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

    @Column(name = "phone_num", length = 12, nullable = false, unique = true)
    @NonNull
    private String phoneNum;

    @Column(name = "current_data")
    private boolean currentData;

    @Override
    public boolean isEmpty() {
        return bank.isEmpty() ||
                phoneNum.isEmpty();
    }
}
