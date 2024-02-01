package org.university.payment_for_utilities.pojo.responses.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.university.payment_for_utilities.pojo.responses.abstract_class.TransliterationResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DistrictResponse extends TransliterationResponse {

}
