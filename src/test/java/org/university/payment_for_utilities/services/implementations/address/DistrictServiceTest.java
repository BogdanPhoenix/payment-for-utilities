package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictUpdateRequest;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.address.DistrictRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DistrictServiceTest extends CrudServiceTest {
    @Autowired
    public DistrictServiceTest(DistrictRepository repository){
        service = new DistrictServiceImpl(repository);
        initRequest();
    }

    private void initRequest(){
        emptyRequest = DistrictRequest
                .builder()
                .uaName("")
                .build();

        firstReques = DistrictRequest
                .builder()
                .uaName("Рівненський")
                .enName("Rivne")
                .build();

        secondRequest = DistrictRequest
                .builder()
                .uaName("Білоцерківський")
                .enName("Belotserkivskyi")
                .build();

        correctUpdateRequest = DistrictUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    void testAddValueThrowInvalidInputData(){
        var withNum = DistrictRequest
                .builder()
                .uaName("Рівн5енський")
                .enName("Rivne")
                .build();
        var withSpecialCharacter = DistrictRequest
                .builder()
                .uaName("Рівн@енський")
                .enName("Rivne")
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(withNum)
        );
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(withSpecialCharacter)
        );
    }
    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    void testUpdateValueCorrectWithOneChangedParameter() {
        var otherName = "other";

        var updateRequest = DistrictUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(DistrictRequest
                        .builder()
                        .uaName("")
                        .enName(otherName)
                        .build()
                )
                .build();

        var response = service.addValue(firstReques);
        var updateResponse = service.updateValue(updateRequest);

        assertEquals(updateResponse.getId(), response.getId());
        assertEquals(updateResponse.getUaName(), response.getUaName());
        assertEquals(updateResponse.getEnName(), otherName);
    }

    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Test
    void testUpdateOblastThrowRequestEmpty(){
        var requestEmpty = DistrictUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = DistrictUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstReques)
                .build();

        var requestNewValueEmpty = DistrictUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(emptyRequest)
                .build();

        assertThrows(EmptyRequestException.class,
                () -> service.updateValue(requestEmpty)
        );

        assertThrows(EmptyRequestException.class,
                () -> service.updateValue(requestOldValueEmpty)
        );

        assertThrows(EmptyRequestException.class,
                () -> service.updateValue(requestNewValueEmpty)
        );
    }

    @Test
    @DisplayName("Check for exceptions when data was transferred in an incorrect format in an update request.")
    void testUpdateValueThrowInvalidInputData(){
        var incorrectRequest = DistrictRequest
                .builder()
                .uaName("Рівне5нський")
                .enName("Rivne")
                .build();

        var correctOnlyOldValue = DistrictUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = DistrictUpdateRequest
                .builder()
                .oldValue(incorrectRequest)
                .newValue(firstReques)
                .build();

        service.addValue(firstReques);

        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyOldValue)
        );
        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyNewValue)
        );
    }
}
