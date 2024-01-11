package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.TableInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "oblasts")
public class Oblast implements TableInfo {
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "oblast_districts",
            joinColumns = @JoinColumn(name = "id_oblast", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_district", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_oblast", "id_district"})
    )
    private List<District> districts;

    @Override
    public boolean isEmpty() {
        return uaName.isBlank() ||
                enName.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull Oblast empty(){
        return Oblast
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
