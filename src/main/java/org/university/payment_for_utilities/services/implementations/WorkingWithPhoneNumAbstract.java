package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.payment_for_utilities.domains.interfaces.TableInfo;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;

public abstract class WorkingWithPhoneNumAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> extends CrudServiceAbstract<T, J> {
    protected WorkingWithPhoneNumAbstract(J repository, String tableName) {
        super(repository, tableName);
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        //TODO Validation is not required, since the request passes either a ready-made object or an object with empty attributes. If the attribute object is empty, it will not be updated.
    }

    protected void validatePhone(PhoneNum phoneNum) throws InvalidInputDataException {
        if (isValidPhoneNum(phoneNum)) {
            return;
        }

        var message = String.format("The phone number entity you provided has not been validated: \"%s\". The phone number entity cannot be null or empty.", phoneNum);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isValidPhoneNum(@NonNull PhoneNum phoneNum) {
        return !phoneNum.isEmpty();
    }
}
