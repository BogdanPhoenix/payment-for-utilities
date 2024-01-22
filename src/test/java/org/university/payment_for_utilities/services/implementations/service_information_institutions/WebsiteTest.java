package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;

@SpringBootTest
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WebsiteTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankWebsiteRequest")
    private WebsiteRequest privateBankWebsiteRequest;
    @Autowired
    @Qualifier("raiffeisenBankWebsiteRequest")
    private WebsiteRequest raiffeisenBankWebsiteRequest;

    @Autowired
    public WebsiteTest(WebsiteService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = privateBankWebsiteRequest;
        secondRequest = raiffeisenBankWebsiteRequest;
        emptyRequest = WebsiteRequest
                .empty();
    }

    @Override
    protected Response updateExpectedResponse(@NonNull Response response) {
        return WebsiteResponse
                .builder()
                .id(response.getId())
                .website("https://raiffeisen_new.ua/")
                .build();
    }

    @Override
    protected Request updateNewValue(@NonNull Response expectedResponse) {
        var response = (WebsiteResponse) expectedResponse;
        return WebsiteRequest
                .builder()
                .website(response.getWebsite())
                .build();
    }

    @Test
    @DisplayName("Check exceptions when the request has an incorrect format of the bank's website.")
    void testWedSiteThrowInvalidInputDataException(){
        var request = WebsiteRequest
                .builder()
                .website("privatbank.ua")
                .build();

        addValueThrowInvalidInputData(request);
    }
}
