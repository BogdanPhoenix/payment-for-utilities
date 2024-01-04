package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.TableInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "settlements",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_type", "zip_code", "id_name"})
)
public class Settlement implements TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NonNull
    private TypeSettlement type;

    @Column(name = "zip_code", length = 5, nullable = false, unique = true)
    @NonNull
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "id_name", nullable = false)
    @NonNull
    private SettlementName name;

    @Column(name = "current_data")
    private boolean currentData;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "settlements")
    private List<District> districts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AddressResidence> addresses;

    @Override
    public boolean isEmpty() {
        return id == null ||
                type.isEmpty() ||
                zipCode .isEmpty() ||
                name.isEmpty();
    }
}
