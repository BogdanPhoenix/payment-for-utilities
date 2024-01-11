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
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

import static org.assertj.core.api.Assertions.assertThat;

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
        settlement = addressRequest
                .settlement();

        emptyRequest = AddressResidenceRequest
                .empty();

        secondRequest = AddressResidenceRequest
                .builder()
                .settlement(settlement)
                .uaNameStreet("вулиця нова")
                .enNameStreet("new street")
                .numHouse("4")
                .numEntrance("")
                .numApartment("")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var response = (AddressResidenceResponse) service.addValue(firstRequest);

        var expectedResponse = AddressResidenceResponse
                .builder()
                .id(response.id())
                .settlement(settlement)
                .uaNameStreet("вулиця стара")
                .enNameStreet("old street")
                .numHouse("41")
                .numEntrance("5")
                .numApartment("305")
                .build();

        var newValue = AddressResidenceRequest
                .builder()
                .settlement(expectedResponse.settlement())
                .uaNameStreet(expectedResponse.uaNameStreet())
                .enNameStreet(expectedResponse.enNameStreet())
                .numHouse(expectedResponse.numHouse())
                .numEntrance(expectedResponse.numEntrance())
                .numApartment("")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(newValue)
                .build();

        var updateResponse = (AddressResidenceResponse) service.updateValue(updateRequest);

        assertThat(updateResponse)
                .isEqualTo(expectedResponse);
    }
}
