package org.university.payment_for_utilities.services.implementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.university.payment_for_utilities.pojo.requests.address.interfaces.Request;
import org.university.payment_for_utilities.pojo.update_request.address.interfaces.UpdateRequest;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;
import org.university.payment_for_utilities.services.interfaces.CrudService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public abstract class CrudServiceTest {
    protected Request firstRequest;
    protected Request secondRequest;
    protected Request emptyRequest;
    protected UpdateRequest correctUpdateRequest;
    protected CrudService service;

    protected abstract void initRequest();
    protected abstract void testUpdateValueCorrectWithOneChangedParameter();
    protected abstract void testUpdateValueThrowRequestEmpty();

    @AfterEach
    public void clearTable(){
        service.removeAll();
    }

    @Test
    @DisplayName("Checks the output of all table entities.")
    void testGetAllEntities(){
        service.addValue(firstRequest);
        service.addValue(secondRequest);

        var entities = service.getAll();

        assertThat(entities)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    @DisplayName("Check for adding data to the database.")
    void testAddValueCorrectName(){
        var response = service.addValue(firstRequest);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("Checking for an exception when the request is empty inside.")
    void testAddValueThrowRequestEmpty(){
        assertThrows(EmptyRequestException.class,
                () -> service.addValue(emptyRequest)
        );
    }

    @Test
    @DisplayName("Checking for an exception when the query passed data that already exists in the database table.")
    void testAddValueThrowDuplicate(){
        service.addValue(firstRequest);
        assertThrows(DuplicateException.class,
                () -> service.addValue(firstRequest)
        );
    }

    protected void testUpdateValueThrowRequestEmpty(UpdateRequest emptyUpdateRequest, UpdateRequest requestOldValueEmpty, UpdateRequest requestNewValueEmpty){
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
    @DisplayName("Checks for exceptions when the update request passes a new area name that already exists in the database table.")
    void testUpdateValueThrowDuplicate(){
        service.addValue(firstRequest);
        service.addValue(secondRequest);

        assertThrows(DuplicateException.class,
                () -> service.updateValue(correctUpdateRequest)
        );
    }

    @Test
    @DisplayName("Checks for exceptions when a user wants to update data that is not in a database table in an update request.")
    void testUpdateValueThrowNotFindEntityInDataBase(){
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.updateValue(correctUpdateRequest)
        );
    }

    @Test
    @DisplayName("Checking the correct deletion of data from the table using the entity identifier.")
    void testRemoveValueCorrect(){
        var responseAdd = service.addValue(firstRequest);
        var responseRemove = service.removeValue(responseAdd.getId());

//        assertEquals(responseAdd, responseRemove);
        assertThat(responseAdd)
                .isEqualTo(responseRemove);
    }

    @Test
    @DisplayName("Checking an exception when the user wants to delete an entity that is not in the table.")
    void testRemoveValueThrowNotFindEntityInDataBaseException(){
        var id = 5L;
        assertThrows(NotFindEntityInDataBaseException.class,
                () -> service.removeValue(id)
        );
    }

    @Test
    @DisplayName("Check if all data is deleted from the table.")
    void testRemoveAll(){
        service.addValue(firstRequest);
        service.addValue(secondRequest);

        var numDeleteItems = service.removeAll();

        assertThat(numDeleteItems)
                .isEqualTo(2L);
    }
}
