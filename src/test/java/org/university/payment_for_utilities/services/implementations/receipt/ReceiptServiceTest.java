package org.university.payment_for_utilities.services.implementations.receipt;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.receipt.ReceiptRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.receipt.ReceiptResponse;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.receipt.ReceiptService;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(ReceiptEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReceiptServiceTest extends CrudServiceTest {
    @Autowired
    @Qualifier("rivneReceiptRequest")
    private ReceiptRequest rivneReceiptRequest;
    @Autowired
    @Qualifier("rivneContract")
    private ContractEntityResponse rivneContract;
    @Autowired
    @Qualifier("kyivReceiptRequest")
    private ReceiptRequest kyivReceiptRequest;
    @Autowired
    @Qualifier("kyivContract")
    private ContractEntityResponse kyivContract;
    @Autowired
    @Qualifier("privateBank")
    private BankResponse privateBank;

    @Autowired
    public ReceiptServiceTest(ReceiptService service) { super(service); }

    @BeforeEach
    @Override
    protected void initRequest() {
        firstRequest = rivneReceiptRequest;
        secondRequest = kyivReceiptRequest;
        emptyRequest = ReceiptRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return ReceiptResponse
                .builder()
                .id(response.getId())
                .contractEntity(kyivContract)
                .bank(privateBank)
                .billMonth(LocalDate.of(2024, Month.JANUARY, 5))
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (ReceiptResponse) expectedResponse;
        return ReceiptRequest
                .builder()
                .contractEntity(Response.EMPTY_PARENT_ENTITY)
                .bank(response.getBank().getId())
                .billMonth(response.getBillMonth())
                .build();
    }

    @ParameterizedTest
    @MethodSource("testBillMonths")
    @DisplayName("Check for exceptions when the specified month and year do not pass validation.")
    void testValidateBillMonths(LocalDate month) {
        var request = (ReceiptRequest) firstRequest;
        request.setBillMonth(month);
        addValueThrowInvalidInputData(request);
    }

    private static @NonNull Stream<Arguments> testBillMonths() {
        return Stream.of(
                Arguments.of(LocalDate.of(2022, Month.APRIL, 1)),
                Arguments.of(LocalDate.of(2024, Month.JULY, 1))
        );
    }

    @Test
    @DisplayName("Check for receipt of all receipts of a registered user by his/her ID.")
    void testFindUserByIdCorrect() {
        service.addValue(firstRequest);
        service.addValue(secondRequest);

        var userId = rivneContract.getRegisteredUser().getId();
        var receipts = ((ReceiptService)service).findByUserId(userId);

        assertThat(receipts)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @DisplayName("Check for an empty list of receipts if the registered user does not have them yet.")
    void testNotFindUserById() {
        service.addValue(firstRequest);
        service.addValue(secondRequest);

        var userId = Long.MAX_VALUE;
        var receipts = ((ReceiptService)service).findByUserId(userId);

        assertThat(receipts)
                .isNotNull()
                .isEmpty();
    }
}
