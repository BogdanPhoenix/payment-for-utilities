package org.university.payment_for_utilities.pojo.responses.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.UnitMeasurementResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TypeOfferResponse extends TransliterationResponse {
    private UnitMeasurementResponse unitMeasurement;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                unitMeasurement.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull TypeOfferResponse empty(){
        return TransliterationResponse
                .initEmpty(builder())
                .unitMeasurement(UnitMeasurementResponse.empty())
                .build();
    }
}
