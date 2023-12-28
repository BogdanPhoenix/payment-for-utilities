package org.university.payment_for_utilities.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_contract", nullable = false)
    @NonNull
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "id_bank", nullable = false)
    @NonNull
    private Bank bank;

    @Column(name = "bill_month_and_year", nullable = false)
    @NonNull
    private Date billMonthAndYear;

    @Column(name = "date_time_create", nullable = false)
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentHistory> paymentHistories;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockAccrualAmount> blockAccrualAmounts;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockMeterReading> blockMeterReadings;
}
