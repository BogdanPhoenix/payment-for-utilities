package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "names_administrative_units")
public class SettlementName extends TransliterationProperty {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private transient List<Settlement> settlements;

    @Contract(" -> new")
    public static @NonNull SettlementName empty(){
        return SettlementName
                .builder()
                .uaName("")
                .enName("")
                .build();
    }
}
