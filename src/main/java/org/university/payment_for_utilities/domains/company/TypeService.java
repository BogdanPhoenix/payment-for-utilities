package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.company.CompanyTariff;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "types_services")
public class TypeService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "unit_measurement", nullable = false, unique = true)
    @NonNull
    private String unitMeasurement;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CompanyTariff> tariffs;
}
