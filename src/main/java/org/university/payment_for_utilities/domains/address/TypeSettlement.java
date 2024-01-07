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
@Table(name = "types_settlement")
public class TypeSettlement implements TableInfo {
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Settlement> settlements;

    @Override
    public boolean isEmpty() {
        return uaName.isEmpty() ||
                enName.isEmpty();
    }
}
