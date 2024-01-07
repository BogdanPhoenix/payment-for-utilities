package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.update_request.address.AddressResidenceUpdateRequest;
import org.university.payment_for_utilities.pojo.update_request.address.SettlementUpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class AddressResidenceServiceTest extends CrudServiceTest {
    private Settlement settlement;
    @Autowired
    @Qualifier("addressResidenceRequest")
    private AddressResidenceRequest addressRequest;

    @Autowired
    public AddressResidenceServiceTest(AddressResidenceService service){
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = addressRequest;
        settlement = addressRequest.getSettlement();

        emptyRequest = AddressResidenceRequest
                .builder()
                .build();

        secondRequest = AddressResidenceRequest
                .builder()
                .settlement(settlement)
                .uaNameStreet("вулиця нова")
                .enNameStreet("new street")
                .numHouse("4")
                .numEntrance("")
                .numApartment("")
                .build();

        correctUpdateRequest = AddressResidenceUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var numHouse = "41";

        var newValue = AddressResidenceRequest
                .builder()
                .settlement(settlement)
                .uaNameStreet("вулиця стара")
                .enNameStreet("old street")
                .numHouse(numHouse)
                .numEntrance("5")
                .numApartment("")
                .build();

        var updateRequest = SettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(newValue)
                .build();

        var response = (AddressResidenceResponse) service.addValue(firstRequest);
        var updateResponse = (AddressResidenceResponse) service.updateValue(updateRequest);

        assertEquals(response.getId(), updateResponse.getId());
        assertEquals(response.getSettlement(), updateResponse.getSettlement());
        assertEquals(numHouse, updateResponse.getNumHouse());
    }

    @Test
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Override
    protected void testUpdateValueThrowRequestEmpty() {
        var emptyUpdateRequest = AddressResidenceUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = AddressResidenceUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = AddressResidenceUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(emptyRequest)
                .build();

        testUpdateValueThrowRequestEmpty(emptyUpdateRequest, requestOldValueEmpty, requestNewValueEmpty);
    }
}
