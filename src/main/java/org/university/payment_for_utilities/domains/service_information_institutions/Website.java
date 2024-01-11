package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.company.Company;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "websites")
public class Website implements TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "website", length = 500, nullable = false, unique = true)
    @NonNull
    private String website;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "website", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "website", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Bank bank;

    @Override
    public boolean isEmpty() {
        return website.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull Website empty(){
        return Website
                .builder()
                .website("")
                .build();
    }
}
