package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.pojo.responses.address.OblastResponse;

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
@Table(name = "oblasts")
public class Oblast extends TransliterationProperty {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "oblast_districts",
            joinColumns = @JoinColumn(name = "id_oblast", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_district", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_oblast", "id_district"})
    )
    private List<District> districts;

    @Override
    public OblastResponse getResponse() {
        var responseBuilder = OblastResponse.builder();
        return super
                .responseTransliterationPropertyBuilder(responseBuilder)
                .build();
    }
}
