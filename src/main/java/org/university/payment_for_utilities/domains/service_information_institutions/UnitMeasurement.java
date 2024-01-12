package org.university.payment_for_utilities.domains.service_information_institutions;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.domains.TransliterationProperty;
import org.university.payment_for_utilities.domains.company.TypeOffer;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "units_measurement")
public class UnitMeasurement implements TransliterationProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

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
    @OneToMany(mappedBy = "unitMeasurement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TypeOffer> offers;

    @Override
    public boolean isEmpty() {
        return uaName.isBlank() ||
                enName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull UnitMeasurement empty(){
        return UnitMeasurement
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
