package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "settlements",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_type", "index", "id_name"})
)
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private TypeSettlement type;

    @Column(name = "index", length = 5, nullable = false, unique = true)
    private String index;

    @ManyToOne
    @JoinColumn(name = "id_name", nullable = false)
    @NonNull
    private SettlementName name;

    @Column(name = "current_data")
    private boolean currentData;

    @ManyToMany(mappedBy = "settlements")
    private List<District> districts;

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AddressResidence> addresses;
}
