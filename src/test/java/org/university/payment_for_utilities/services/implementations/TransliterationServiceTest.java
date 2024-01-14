package org.university.payment_for_utilities.services.implementations;

import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;

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

    protected void testUpdateValueThrowInvalidInputData(Request incorrectRequest) {
        var response = service.addValue(firstRequest);

        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(response.id(), incorrectRequest)
        );
    }
}
