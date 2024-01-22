package org.university.payment_for_utilities.pojo.responses.receipt;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.university.payment_for_utilities.pojo.responses.abstract_class.CounterSearcherResponse;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlockMeterReadingResponse extends CounterSearcherResponse {
}
