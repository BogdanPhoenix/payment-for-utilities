package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.payment_for_utilities.domains.TableInfo;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;

@Slf4j
public abstract class PhoneNumServiceAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> extends CrudServiceAbstract<T, J> {
    private static final String PHONE_NUM_TEMPLATE = "^380\\d{9}$";

    protected PhoneNumServiceAbstract(J repository, String tableName) {
        super(repository, tableName);
    }

    protected void validatePhoneNum(String phoneNum) throws InvalidInputDataException {
        if (isValidPhoneNum(phoneNum)) {
            return;
        }

        var message = String.format("The mobile phone number you provided was not validated: \"%s\". The phone number must consist of twelve digits and start with \"380\".", phoneNum);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    @Contract(pure = true)
    private boolean isValidPhoneNum(@NonNull String phoneNum) {
        return phoneNum
                .matches(PHONE_NUM_TEMPLATE);
    }
}
