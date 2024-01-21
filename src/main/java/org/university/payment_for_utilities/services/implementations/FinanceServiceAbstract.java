package org.university.payment_for_utilities.services.implementations;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.university.payment_for_utilities.domains.abstract_class.TableInfo;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;

import java.math.BigDecimal;

public abstract class FinanceServiceAbstract<T extends TableInfo, J extends JpaRepository<T, Long>> extends CrudServiceAbstract<T, J> {
    private static final int FRACTIONAL_ACCURACY = 2;
    private static final String COMPLEMENT_SYMBOL = "0";
    private static final String FIXED_COST_TEMPLATE = "^\\d+\\.\\d{1,2}$";

    protected FinanceServiceAbstract(J repository, String tableName) {
        super(repository, tableName);
    }

    protected @NonNull BigDecimal convertStringToBigDecimal(@NonNull String money) {
        int indexPoint = money.lastIndexOf(".");
        int fractionalValue = money.length() - indexPoint - 1;

        if(fractionalValue < FRACTIONAL_ACCURACY) {
            money += COMPLEMENT_SYMBOL.repeat(FRACTIONAL_ACCURACY - fractionalValue);
        }

        return new BigDecimal(money);
    }

    protected void validateFinance(@NonNull String fixedCost) throws InvalidInputDataException {
        if(isFixedCost(fixedCost)){
            return;
        }

        var message = String.format("Fixed value provided by you: \"%s\" has not been validated. It should contain only a fractional number and should be separated by a period.", fixedCost);
        throwRuntimeException(message, InvalidInputDataException::new);
    }

    private boolean isFixedCost(@NonNull String fixedCost) {
        return fixedCost.isBlank() || fixedCost
                .matches(FIXED_COST_TEMPLATE);
    }
}
