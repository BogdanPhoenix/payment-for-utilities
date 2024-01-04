package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.domains.address.SettlementName;
import org.university.payment_for_utilities.domains.address.TypeSettlement;
import org.university.payment_for_utilities.pojo.requests.address.SettlementNameRequest;
import org.university.payment_for_utilities.pojo.requests.address.SettlementRequest;
import org.university.payment_for_utilities.pojo.requests.address.TypeSettlementRequest;
import org.university.payment_for_utilities.pojo.responses.address.SettlementNameResponse;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;
import org.university.payment_for_utilities.pojo.update_request.address.SettlementUpdateRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.address.SettlementNameService;
import org.university.payment_for_utilities.services.interfaces.address.SettlementService;
import org.university.payment_for_utilities.services.interfaces.address.TypeSettlementService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SettlementServiceTest extends CrudServiceTest {
    private final TypeSettlementService typeService;
    private final SettlementNameService nameService;

    private TypeSettlement type;
    private SettlementName nameRivne;
    private SettlementName nameKyiv;

    @Autowired
    public SettlementServiceTest(SettlementService service, TypeSettlementService typeService, SettlementNameService nameService) {
        this.service = service;
        this.typeService = typeService;
        this.nameService = nameService;
        initRequest();
    }

    @Override
    protected void initRequest() {
        var typeRequest = TypeSettlementRequest
                .builder()
                .uaName("тестове значення")
                .enName("test value")
                .build();

        var nameRivneRequest = SettlementNameRequest
                .builder()
                .uaName("Рівне")
                .enName("Rivne")
                .build();

        var nameKyivRequest = SettlementNameRequest
                .builder()
                .uaName("Київ")
                .enName("Kyiv")
                .build();

        type = createType(typeRequest);
        nameRivne = createName(nameRivneRequest);
        nameKyiv = createName(nameKyivRequest);

        emptyRequest = SettlementRequest
                .builder()
                .zipCode("")
                .build();

        firstRequest = SettlementRequest
                .builder()
                .type(type)
                .zipCode("12345")
                .name(nameRivne)
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

    private TypeSettlement createType(TypeSettlementRequest request) {
        var response = (TypeSettlementResponse) typeService.addValue(request);
        return TypeSettlement
                .builder()
                .id(response.getId())
                .uaName(response.getUaName())
                .enName(response.getEnName())
                .currentData(true)
                .build();
    }

    private SettlementName createName(SettlementNameRequest request){
        var response = (SettlementNameResponse) nameService.addValue(request);
        return SettlementName
                .builder()
                .id(response.getId())
                .uaName(response.getUaName())
                .enName(response.getEnName())
                .currentData(true)
                .build();
    }

    @Override
    public void clearTable() {
        super.clearTable();
        typeService.removeAll();
        nameService.removeAll();
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

    @Test
    @DisplayName("Check for exceptions when an invalid zip code format was passed in the request.")
    void testIndexThrowInvalidInputData(){
        var incorrectIndexWithLetterRequest  = SettlementRequest
                .builder()
                .type(type)
                .zipCode("d5645")
                .name(nameRivne)
                .build();

        var incorrectIndexWithSpecialCharacterRequest  = SettlementRequest
                .builder()
                .type(type)
                .zipCode("45$36")
                .name(nameKyiv)
                .build();

        var incorrectIndexWithUnequalLengthRequest  = SettlementRequest
                .builder()
                .type(type)
                .zipCode("4")
                .name(nameRivne)
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(incorrectIndexWithLetterRequest)
        );

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(incorrectIndexWithSpecialCharacterRequest)
        );

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(incorrectIndexWithUnequalLengthRequest)
        );
    }
}
