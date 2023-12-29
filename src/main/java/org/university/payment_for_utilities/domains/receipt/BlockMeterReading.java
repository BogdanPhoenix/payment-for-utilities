package org.university.payment_for_utilities.domains.receipt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "blocks_meter_readings")
public class BlockMeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_receipt", nullable = false, unique = true)
    @NonNull
    private Receipt receipt;

    @Column(name = "prev_value_counter", nullable = false)
    @NonNull
    private Float prevValueCounter;

    @Column(name = "current_value_counter", nullable = false)
    @NonNull
    private Float currentValueCounter;

    @Column(name = "current_data")
    private boolean currentData;
}
