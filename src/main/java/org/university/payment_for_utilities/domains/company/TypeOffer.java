package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.interfaces.TransliterationProperty;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "types_offers",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id_unit", "en_name"})
    }
)
public class TypeOffer implements TransliterationProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_unit", nullable = false)
    @NonNull
    private UnitMeasurement unitMeasurement;

    @Column(name = "ua_name", nullable = false, unique = true)
    @NonNull
    private String uaName;

    @Column(name = "en_name", nullable = false, unique = true)
    @NonNull
    private String enName;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CompanyTariff> tariffs;

    @Override
    public boolean isEmpty() {
        return uaName.isBlank() ||
                enName.isBlank() ||
                unitMeasurement.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull TypeOffer empty(){
        return TypeOffer
                .builder()
                .uaName("")
                .enName("")
                .unitMeasurement(UnitMeasurement.empty())
                .build();
    }
}
