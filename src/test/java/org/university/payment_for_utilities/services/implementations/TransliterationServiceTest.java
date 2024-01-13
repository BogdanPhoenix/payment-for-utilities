package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public abstract class TransliterationServiceTest extends CrudServiceTest {
    protected abstract void testAddValueThrowInvalidInputData();
    protected abstract void testUpdateValueThrowInvalidInputData();

    protected void testAddValueThrowInvalidInputData(Request withNum, Request withSpecialCharacter) {
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(withNum)
        );
        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(withSpecialCharacter)
        );
    }

    protected void testUpdateValueCorrectWithOneChangedParameter(@NonNull TransliterationRequest newValue){
        var updateRequest = UpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(newValue)
                .build();

        var otherName = newValue
                .enName();

        var response = (TransliterationResponse) service.addValue(firstRequest);
        var updateResponse = (TransliterationResponse) service.updateValue(updateRequest);

        assertEquals(updateResponse.id(), response.id());
        assertEquals(updateResponse.uaName(), response.uaName());
        assertEquals(updateResponse.enName(), otherName);
    }

    protected void testUpdateValueThrowInvalidInputData(Request incorrectRequest) {
        var correctOnlyOldValue = UpdateRequest
                .builder()
                .oldValue(firstRequest)
                .newValue(incorrectRequest)
                .build();

        var correctOnlyNewValue = UpdateRequest
                .builder()
                .oldValue(incorrectRequest)
                .newValue(firstRequest)
                .build();

        service.addValue(firstRequest);

        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyOldValue)
        );
        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyNewValue)
        );
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    void testUpdateValueCorrect(){
        var response = service.addValue(firstRequest);
        var updateResponse = (TransliterationResponse) service.updateValue(correctUpdateRequest);
        var newValue = (TransliterationRequest) correctUpdateRequest.getNewValue();

        assertEquals(updateResponse.id(), response.id());
        assertEquals(updateResponse.uaName(), newValue.uaName());
        assertEquals(updateResponse.enName(), newValue.enName());
    }

}
