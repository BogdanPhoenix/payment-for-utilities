package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.domains.address.Settlement;
import org.university.payment_for_utilities.pojo.requests.address.AddressResidenceRequest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.AddressResidenceService;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class AddressResidenceServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("addressResidenceRequest")
    private AddressResidenceRequest addressRequest;
    @Autowired
    @Qualifier("addressKyivRequest")
    private AddressResidenceRequest addressKyivRequest;

    @Autowired
    public AddressResidenceServiceTest(AddressResidenceService service){
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = addressRequest;
        secondRequest = addressKyivRequest;
        emptyRequest = AddressResidenceRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response){
        return AddressResidenceResponse
                .builder()
                .id(response.id())
                .settlement(addressKyivRequest.settlement())
                .uaNameStreet("вулиця нова")
                .enNameStreet("new street")
                .numHouse("41")
                .numEntrance("5")
                .numApartment("305")
                .build();

    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse){
        var response = (AddressResidenceResponse) expectedResponse;
        return AddressResidenceRequest
                .builder()
                .settlement(Settlement.empty())
                .uaNameStreet("")
                .enNameStreet("")
                .numHouse(response.numHouse())
                .numEntrance(response.numEntrance())
                .numApartment(response.numApartment())
                .build();
    }
}
