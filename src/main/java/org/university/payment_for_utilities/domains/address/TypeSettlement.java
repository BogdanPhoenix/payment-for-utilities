package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "types_settlement")
public class TypeSettlement extends TransliterationProperty {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "type", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<Settlement> settlements;

    @Override
    public Response getResponse() {
        var responseBuilder = TypeSettlementResponse.builder();
        return super
                .responseInit(responseBuilder)
                .build();
    }

    @Contract(" -> new")
    public static @NonNull TypeSettlement empty(){
        return TransliterationProperty
                .initEmpty(builder())
                .build();
    }
}
