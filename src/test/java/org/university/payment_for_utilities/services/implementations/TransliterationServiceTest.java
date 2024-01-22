package org.university.payment_for_utilities.services.implementations;

import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public abstract class TransliterationServiceTest extends CrudServiceTest {
    protected abstract void testAddValueThrowInvalidInputData();
    protected abstract void testUpdateValueThrowInvalidInputData();

    protected void testAddValueThrowInvalidInputData(Request withNum, Request withSpecialCharacter) {
        addValueThrowInvalidInputData(withNum);
        addValueThrowInvalidInputData(withSpecialCharacter);
    }

    protected void testUpdateValueThrowInvalidInputData(Request incorrectRequest) {
        var response = service.addValue(firstRequest);

        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(response.getId(), incorrectRequest)
        );
    }
}
