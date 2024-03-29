package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.CounterSearcher;
import org.university.payment_for_utilities.pojo.responses.receipt.BlockMeterReadingResponse;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "blocks_meter_readings")
public class BlockMeterReading extends CounterSearcher {
    @Override
    public BlockMeterReadingResponse getResponse() {
        var responseBuilder = BlockMeterReadingResponse.builder();
        return super
                .responseCounterSearcherBuilder(responseBuilder)
                .build();
    }
}
