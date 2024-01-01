package org.university.payment_for_utilities.services.interfaces.address;

import lombok.NonNull;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictRequest;
import org.university.payment_for_utilities.domains.pojo.requests.address.DistrictUpdateRequest;
import org.university.payment_for_utilities.domains.pojo.responses.address.DistrictResponse;
import org.university.payment_for_utilities.exceptions.DuplicateException;
import org.university.payment_for_utilities.exceptions.EmptyRequestException;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.exceptions.NotFindEntityInDataBaseException;

public interface DistrictService {
    /**
     * Creates an object according to the specified request.
     *
     * @param request must not be {@literal null}.
     * @return response for the saved entity; never {@literal null}.
     * @throws EmptyRequestException if the variables of the given entity are {@literal null} or {@literal empty}.
     * @throws InvalidInputDataException if the variables of this entity have an incorrect data format.
     * @throws DuplicateException if the table contains an entity value that is passed in the query.
     */
    DistrictResponse addValue(@NonNull DistrictRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException;

    /**
     * Updates the value of an entity in a database table.
     *
     * @param request must not be {@literal null}.
     * @return response for the updated entity; never {@literal null}.
     * @throws EmptyRequestException if the variables of the given entity are {@literal null} or {@literal empty}.
     * @throws InvalidInputDataException if the variables of this entity have an incorrect data format.
     * @throws DuplicateException if the table contains an entity value by which you want to update the value of the current entity.
     * @throws NotFindEntityInDataBaseException if the table does not contain the entity you want to update.
     */
    DistrictResponse updateValue(@NonNull DistrictUpdateRequest request) throws EmptyRequestException, InvalidInputDataException, DuplicateException, NotFindEntityInDataBaseException;

    /**
     * Removes an entity from the table by the specified identifier.
     *
     * @param id must not be {@literal null}.
     * @return response for the deleted entity; never {@literal null}.
     * @throws NotFindEntityInDataBaseException if the table does not contain the entity to be deleted.
     */
    DistrictResponse removeValue(Long id) throws NotFindEntityInDataBaseException;

    /**
     * Deletes all entities from the table.
     *
     * @return the number of deleted entities.
     */
    Long removeAll();
}
