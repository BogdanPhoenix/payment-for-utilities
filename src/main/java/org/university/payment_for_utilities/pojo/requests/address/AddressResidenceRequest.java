package org.university.payment_for_utilities.pojo.requests.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressResidenceRequest extends Request {
    private SettlementResponse settlement;
    private String uaNameStreet;
    private String enNameStreet;
    private String numHouse;
    private String numEntrance;
    private String numApartment;

    @Override
    public boolean isEmpty() {
        return this.settlement.isEmpty() ||
                this.uaNameStreet.isBlank() ||
                this.enNameStreet.isBlank() ||
                this.numHouse.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull AddressResidenceRequest empty(){
        return AddressResidenceRequest
                .builder()
                .settlement(SettlementResponse.empty())
                .uaNameStreet("")
                .enNameStreet("")
                .numHouse("")
                .numEntrance("")
                .numApartment("")
                .build();
    }
}
