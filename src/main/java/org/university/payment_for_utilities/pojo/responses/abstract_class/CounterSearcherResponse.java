package org.university.payment_for_utilities.pojo.responses.abstract_class;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CounterSearcherResponse extends ReceiptSearcherResponse {
    private Float prevValueCounter;
    private Float currentValueCounter;
}
