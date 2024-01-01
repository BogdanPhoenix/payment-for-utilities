package org.university.payment_for_utilities.services.implementations.address;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastRequest;
import org.university.payment_for_utilities.domains.pojo.requests.address.OblastUpdateRequest;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.repositories.address.OblastRepository;
import org.university.payment_for_utilities.services.interfaces.address.OblastService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OblastServiceTest {
    private static OblastRequest correctRequest;
    private static OblastUpdateRequest correctUpdateRequest;

    @Autowired
    private OblastRepository repository;
    private OblastService service;

    @BeforeAll
    static void initRequest(){
        correctRequest = OblastRequest
                .builder()
                .uaName("Рівненська")
                .enName("Rivnenska")
                .build();

        correctUpdateRequest = OblastUpdateRequest
                .builder()
                .oldUaValue(correctRequest.getUaName())
                .oldEnValue(correctRequest.getEnName())
                .newUaValue("Київська")
                .newEnValue("Kyivska")
                .build();
    }

    @BeforeEach
    void initialize(){
        service = new OblastServiceImpl(repository);
    }

    @AfterEach
    void clearTable(){
        service.removeAll();
    }

    @Test
    @DisplayName("Check for adding data to the database.")
    void testAddOblastCorrectName(){
        var actualWithCyrillicLetters = service.addValue(correctRequest);
        assertThat(actualWithCyrillicLetters.getId()).isNotNull();
    }

    @Test
    @DisplayName("Checking for an exception when the request is empty inside.")
    void testAddOblastThrowRequestEmpty(){
        var emptyRequest = OblastRequest
                .builder()
                .build();

        assertThrows(EmptyRequestException.class,
                () -> service.addValue(emptyRequest)
        );
    }

    @Test
    @DisplayName("Checking for an exception when data in an incorrect format was passed in a request.")
    void testAddOblastThrowInvalidInputData(){
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
    @DisplayName("Checking for an exception when the query passed data that already exists in the database table.")
    void testAddOblastThrowDuplicate(){
        service.addValue(correctRequest);
        assertThrows(DuplicateException.class,
                () -> service.addValue(correctRequest)
        );
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    void testUpdateOblastCorrect(){
        var oblastResponse = service.addValue(correctRequest);
        var updateResponse = service.updateValue(correctUpdateRequest);

        assertEquals(updateResponse.getId(), oblastResponse.getId());
        assertEquals(updateResponse.getUaName(), correctUpdateRequest.getNewUaValue());
        assertEquals(updateResponse.getEnName(), correctUpdateRequest.getNewEnValue());
    }

    @DisplayName("Check for exceptions when the update request is empty inside or one of its fields is empty.")
    @Test
    void testUpdateOblastThrowRequestEmpty(){
        var requestEmpty = OblastUpdateRequest
                .builder()
                .build();

        var requestOldValueEmpty = OblastUpdateRequest
                .builder()
                .oldUaValue("")
                .oldEnValue("")
                .newUaValue("Київська")
                .newEnValue("Kyivska")
                .build();

        var requestNewValueEmpty = OblastUpdateRequest
                .builder()
                .oldUaValue("Рівненська")
                .oldEnValue("Rivnenska")
                .newUaValue("")
                .newEnValue("")
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
    void testUpdateOblastThrowInvalidInputData(){
        var correctOnlyOldValue = OblastUpdateRequest
                .builder()
                .oldUaValue("Рівненська")
                .oldEnValue("Rivnenska")
                .newUaValue("Київ5ська")
                .newEnValue("Kyivska")
                .build();

        var correctOnlyNewValue = OblastUpdateRequest
                .builder()
                .oldUaValue("Рівн#енська")
                .oldEnValue("Rivnenska")
                .newUaValue("Київська")
                .newEnValue("Kyivska")
                .build();

        service.addValue(correctRequest);

        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyOldValue)
        );
        assertThrows(InvalidInputDataException.class,
                () -> service.updateValue(correctOnlyNewValue)
        );
    }

    @Test
    @DisplayName("Checks for exceptions when the update request passes a new area name that already exists in the database table.")
    void testUpdateOblastThrowDuplicate(){
        var secondValue = OblastRequest
                .builder()
                .uaName("Київська")
                .enName("Kyivska")
                .build();

        service.addValue(correctRequest);
        service.addValue(secondValue);

        assertThrows(DuplicateException.class,
                () -> service.updateValue(correctUpdateRequest)
        );
    }

    @Test
    @DisplayName("Checks for exceptions when a user wants to update data that is not in a database table in an update request.")
    void testUpdateOblastThrowNotFindEntityInDataBase(){
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.updateValue(correctUpdateRequest)
        );
    }

    @DisplayName("Checking the correct deletion of data from the table using the entity identifier.")
    @Test
    void testRemoveOblastCorrect(){
        var responseAdd = service.addValue(correctRequest);
        var responseRemove = service.removeValue(responseAdd.getId());

        assertEquals(responseAdd, responseRemove);
    }

    @DisplayName("Checking an exception when the user wants to delete an entity that is not in the table.")
    @Test
    void testRemoveOblastThrowNotFindEntityInDataBaseException(){
        var id = 5L;
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.removeValue(id)
        );
    }

    @DisplayName("Check if all data is deleted from the table.")
    @Test
    void testRemoveAll(){
        var secondValue = OblastRequest
                .builder()
                .uaName("Київська")
                .enName("Kyivska")
                .build();

        service.addValue(correctRequest);
        service.addValue(secondValue);

        var numDeleteItems = service.removeAll();

        assertThat(numDeleteItems)
                .isEqualTo(2L);
    }
}
