package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.domains.TransliterationProperty;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "districts")
public class District implements TransliterationProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ua_name", nullable = false, unique = true)
    @NonNull
    private String uaName;

    @Column(name = "en_name", nullable = false, unique = true)
    @NonNull
    private String enName;

    @Column(name = "current_data")
    private boolean currentData;

    @ManyToMany(mappedBy = "districts")
    private List<Oblast> oblasts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "district_settlements",
            joinColumns = @JoinColumn(name = "id_district", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_settlement", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_district", "id_settlement"})
    )
    private List<Settlement> settlements;

    @Override
    public boolean isEmpty() {
        return uaName.isBlank() ||
                enName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull District empty(){
        return District
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
