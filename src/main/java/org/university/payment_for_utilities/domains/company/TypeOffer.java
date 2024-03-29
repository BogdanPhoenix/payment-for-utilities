package org.university.payment_for_utilities.domains.company;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TransliterationProperty;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;

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
@Table(name = "types_offers",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id_unit", "en_name"})
    }
)
public class TypeOffer extends TransliterationProperty {
    @ManyToOne
    @JoinColumn(name = "id_unit", nullable = false)
    @NonNull
    private UnitMeasurement unitMeasurement;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "type", cascade={MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<CompanyTariff> tariffs;

    @Override
    public TypeOfferResponse getResponse() {
        var responseBuilder = TypeOfferResponse.builder();
        return super
                .responseTransliterationPropertyBuilder(responseBuilder)
                .unitMeasurement(this.unitMeasurement.getResponse())
                .build();
    }
}
