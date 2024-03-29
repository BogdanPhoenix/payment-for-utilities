package org.university.payment_for_utilities.domains.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;

import java.util.List;
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
@EqualsAndHashCode(callSuper = false)
@Table(name = "settlements",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_type", "zip_code", "id_name"})
)
public class Settlement extends TableInfo {
    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NonNull
    private TypeSettlement type;

    @Column(name = "zip_code", length = 5, nullable = false, unique = true)
    @NonNull
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "id_name", nullable = false)
    @NonNull
    private SettlementName name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "settlements")
    private List<District> districts;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "settlement", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private Set<AddressResidence> addresses;

    @Override
    public SettlementResponse getResponse() {
        var responseBuilder = SettlementResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .type(this.type.getResponse())
                .zipCode(this.zipCode)
                .name(this.name.getResponse())
                .build();
    }
}
