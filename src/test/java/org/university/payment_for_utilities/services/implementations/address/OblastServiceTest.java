package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastUpdateRequest;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.repositories.address.OblastRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OblastServiceTest extends CrudServiceTest {
    @Autowired
    public OblastServiceTest(OblastRepository repository){
        service = new OblastServiceImpl(repository);
        initRequest();
    }

    private void initRequest(){
        emptyRequest = OblastRequest
                .builder()
                .uaName("")
                .build();

        firstReques = OblastRequest
                .builder()
                .uaName("Рівненська")
                .enName("Rivnenska")
                .build();

        secondRequest = OblastRequest
                .builder()
                .uaName("Київська")
                .enName("Kyivska")
                .build();

        correctUpdateRequest = OblastUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(secondRequest)
                .build();
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    void testAddValueThrowInvalidInputData(){
        var withNum = OblastRequest
                .builder()
                .uaName("Рівн5енська")
                .enName("Rivnenska")
                .build();
        var withSpecialCharacter = OblastRequest
                .builder()
                .uaName("Рівн@енська")
                .enName("Rivnenska")
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
    void testUpdateValueCorrectWithOneChangedParameter(){
        var otherName = "other";

        var updateRequest = OblastUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(OblastRequest
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

    @Test
    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    void testUpdateValueThrowRequestEmpty(){
        var emptyUpdateRequest = OblastUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = OblastUpdateRequest
                .builder()
                .oldValue(emptyRequest)
                .newValue(firstReques)
                .build();

        var requestNewValueEmpty = OblastUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(emptyRequest)
                .build();

        assertThrows(EmptyRequestException.class,
                () -> service.updateValue(emptyUpdateRequest)
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
        var incorrectRequest = OblastRequest
                .builder()
                .uaName("Рівне5нська")
                .enName("Rivne")
                .build();

        var correctOnlyOldValue = OblastUpdateRequest
                .builder()
                .oldValue(firstReques)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = OblastUpdateRequest
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
