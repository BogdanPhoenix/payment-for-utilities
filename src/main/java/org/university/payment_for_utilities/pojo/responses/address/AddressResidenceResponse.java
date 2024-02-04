package org.university.payment_for_utilities.pojo.responses.address;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Contract;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AddressResidenceResponse extends Response {
    private SettlementResponse settlement;
    private String uaNameStreet;
    private String enNameStreet;
    private String numHouse;
    private String numEntrance;
    private String numApartment;

    @Override
    public boolean isEmpty() {
        return settlement.isEmpty() ||
                uaNameStreet.isBlank() ||
                enNameStreet.isBlank() ||
                numHouse.isBlank();
    }

    @Contract(" -> new")
    public static @NonNull AddressResidenceResponse empty() {
        return Response
                .initEmpty(builder())
                .settlement(SettlementResponse.empty())
                .uaNameStreet("")
                .enNameStreet("")
                .numHouse("")
                .numEntrance("")
                .numApartment("")
                .build();
    }
}
