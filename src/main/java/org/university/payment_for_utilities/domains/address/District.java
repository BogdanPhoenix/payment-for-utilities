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
@Table(name = "districts")
public class District {
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "district_settlements",
            joinColumns = @JoinColumn(name = "id_district", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_settlement", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_district", "id_settlement"})
    )
    private List<Settlement> settlements;
}
