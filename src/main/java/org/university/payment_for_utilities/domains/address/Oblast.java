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
@Table(name = "oblasts")
public class Oblast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Column(name = "current_data")
    private boolean currentData;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "oblast_districts",
            joinColumns = @JoinColumn(name = "id_oblast", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_district", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_oblast", "id_district"})
    )
    private List<District> districts;
}
