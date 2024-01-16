package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "settlements",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_type", "zip_code", "id_name"})
)
public class Settlement extends TableInfo {
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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "settlements")
    private transient List<District> districts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<AddressResidence> addresses;

    @Override
    public boolean isEmpty() {
        return type.isEmpty() ||
                zipCode.isBlank() ||
                name.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull Settlement empty(){
        return Settlement
                .builder()
                .type(TypeSettlement.empty())
                .zipCode("")
                .name(SettlementName.empty())
                .build();
    }
}
