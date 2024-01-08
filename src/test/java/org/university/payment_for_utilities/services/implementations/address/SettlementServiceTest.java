package org.university.payment_for_utilities.services.implementations.address;

import lombok.NonNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;
import org.university.payment_for_utilities.pojo.update_request.address.SettlementUpdateRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(AddressEntitiesRequestTestContextConfiguration.class)
class SettlementServiceTest extends CrudServiceTest {
    private TypeSettlement type;

    @Autowired
    private SettlementNameServiceImpl nameService;
    @Autowired
    @Qualifier("nameKyivRequest")
    private SettlementNameRequest nameKyivRequest;
    @Autowired
    @Qualifier("settlementRequest")
    private SettlementRequest settlementRequest;

    @Autowired
    public SettlementServiceTest(SettlementService service) {
        this.service = service;
    }

    @BeforeEach
    @Override
    protected void initRequest() {
        var nameKyiv = createName(nameKyivRequest);
        firstRequest = settlementRequest;
        type = settlementRequest.getType();

        emptyRequest = SettlementRequest
                .builder()
                .zipCode("")
                .build();

        secondRequest = SettlementRequest
                .builder()
                .type(type)
                .zipCode("54321")
                .name(nameKyiv)
                .build();

        correctUpdateRequest = SettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(secondRequest)
                .build();
    }

    private SettlementName createName(SettlementNameRequest request){
        var response = nameService.addValue(request);
        return nameService.createEntity(response);
    }

    @AfterEach
    @Override
    public void clearTable() {
        super.clearTable();
        nameService.removeValue(nameKyivRequest);
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newIndex = "14523";
        var name = SettlementName
                .builder()
                .uaName("")
                .enName("")
                .build();

        var newValue = SettlementRequest
                .builder()
                .type(type)
                .zipCode(newIndex)
                .name(name)
                .build();

        var updateRequest = SettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(newValue)
                .build();

        var response = (SettlementResponse) service.addValue(firstRequest);
        var updateResponse = (SettlementResponse) service.updateValue(updateRequest);

        assertEquals(updateResponse.getId(), response.getId());
        assertEquals(updateResponse.getType(), response.getType());
        assertEquals(updateResponse.getZipCode(), newIndex);
        assertEquals(updateResponse.getName(), response.getName());
    }

    @Test
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Override
    protected void testUpdateValueThrowRequestEmpty() {
        var emptyUpdateRequest = SettlementUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = SettlementUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstRequest)
                .build();

        var requestNewValueEmpty = SettlementUpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(emptyRequest)
                .build();

        testUpdateValueThrowRequestEmpty(emptyUpdateRequest, requestOldValueEmpty, requestNewValueEmpty);
    }

    @ParameterizedTest
    @MethodSource("testPhoneZipCodes")
    @DisplayName("Check for exceptions when an invalid zip code format was passed in the request.")
    void testIndexThrowInvalidInputData(String zipCode){
        var request = (SettlementRequest) firstRequest;

        request.setZipCode(zipCode);
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }

    private static @NonNull Stream<Arguments> testPhoneZipCodes(){
        return Stream.of(
                Arguments.of("d5645"),
                Arguments.of("45$36"),
                Arguments.of("4")
        );
    }
}
