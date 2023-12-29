package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "company_phone_nums")
public class CompanyPhoneNum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    @NonNull
    private Company company;

    @Column(name = "phone_num", length = 20, nullable = false, unique = true)
    @NonNull
    private String phoneNum;

    @Column(name = "current_data")
    private boolean currentData;
}
