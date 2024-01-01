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
@Table(name = "names_administrative_units")
public class SettlementName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ua_name", nullable = false, unique = true)
    @NonNull
    private String ua_name;

    @Column(name = "en_name", nullable = false, unique = true)
    @NonNull
    private String en_name;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Settlement> settlements;
}
