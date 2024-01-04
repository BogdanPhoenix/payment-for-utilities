package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.TransliterationRequest;
import org.university.payment_for_utilities.pojo.responses.address.interfaces.TransliterationResponse;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
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

    protected void testUpdateValueCorrectWithOneChangedParameter(@NonNull UpdateRequest updateRequest){
        var transliterationRequest = (TransliterationRequest) updateRequest.getNewValue();
        var otherName = transliterationRequest
                .getEnName();

        var response = (TransliterationResponse) service.addValue(firstRequest);
        var updateResponse = (TransliterationResponse) service.updateValue(updateRequest);

        assertEquals(updateResponse.getId(), response.getId());
        assertEquals(updateResponse.getUaName(), response.getUaName());
        assertEquals(updateResponse.getEnName(), otherName);
    }

    protected void testUpdateValueThrowInvalidInputData(UpdateRequest correctOnlyOldValue, UpdateRequest correctOnlyNewValue) {
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

        assertEquals(updateResponse.getId(), response.getId());
        assertEquals(updateResponse.getUaName(), newValue.getUaName());
        assertEquals(updateResponse.getEnName(), newValue.getEnName());
    }

}
