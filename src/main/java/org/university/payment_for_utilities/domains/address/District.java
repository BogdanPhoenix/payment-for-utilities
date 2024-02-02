package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.address.DistrictResponse;

import java.util.List;

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
@Table(name = "districts")
public class District extends TransliterationProperty {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "districts")
    private List<Oblast> oblasts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "district_settlements",
            joinColumns = @JoinColumn(name = "id_district", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_settlement", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_district", "id_settlement"})
    )
    private List<Settlement> settlements;

    @Contract(" -> new")
    public static @NonNull District empty(){
        return TransliterationProperty
                .initEmpty(builder())
                .build();
    }

    @Override
    public Response getResponse() {
        var responseBuilder = DistrictResponse.builder();
        return super
                .responseInit(responseBuilder)
                .build();
    }
}
