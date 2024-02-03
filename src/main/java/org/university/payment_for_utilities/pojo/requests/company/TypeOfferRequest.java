package org.university.payment_for_utilities.pojo.requests.company;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.UnitMeasurementResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TypeOfferRequest extends TransliterationRequest {
    private UnitMeasurementResponse unitMeasurement;
    @Override
    public boolean isEmpty() {
        return super.isEmpty() ||
                this.unitMeasurement.isEmpty();
    }

    @Contract(" -> new")
    public static @NonNull TypeOfferRequest empty(){
        return TransliterationRequest
                .initEmpty(builder())
                .unitMeasurement(UnitMeasurementResponse.empty())
                .build();
    }
}
