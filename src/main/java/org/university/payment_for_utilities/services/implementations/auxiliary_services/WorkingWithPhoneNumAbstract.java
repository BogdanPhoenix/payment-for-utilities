package org.university.payment_for_utilities.services.implementations.auxiliary_services;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.repositories.service_information_institutions.PhoneNumRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;

public abstract class WorkingWithPhoneNumAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> extends CrudServiceAbstract<T, J> {
    private final PhoneNumRepository phoneNumRepository;

    protected WorkingWithPhoneNumAbstract(
            J repository,
            String tableName,
            PhoneNumRepository phoneNumRepository
    ) {
        super(repository, tableName);
        this.phoneNumRepository = phoneNumRepository;
    }

    // No additional validation methods are required. It is enough to check for an empty query
    // during insertion and duplication during insertion and update of data.
    @Override
    protected void validationProcedureRequest(@NonNull Request request) throws InvalidInputDataException {

    }

    protected @NonNull PhoneNum getPhoneNum(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(phoneNumRepository, id);
    }
}
