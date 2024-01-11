package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.services.implementations.CrudServiceTest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
class WebsiteTest extends CrudServiceTest {
    @Autowired
    @Qualifier("privateBankWebsiteRequest")
    private WebsiteRequest privateBankWebsiteRequest;

    @Autowired
    public WebsiteTest(WebsiteService service) { this.service = service; }

    @BeforeEach
    @Override
    protected void initRequest(){
        firstRequest = privateBankWebsiteRequest;

        emptyRequest = WebsiteRequest
                .builder()
                .build();

        secondRequest = WebsiteRequest
                .builder()
                .website("https://raiffeisen.ua/")
                .build();

        super.initRequest();
    }

    @Test
    @DisplayName("Check if an existing entity is successfully updated in the database table.")
    @Override
    protected void testUpdateValueCorrectWithOneChangedParameter() {
        var newValue = WebsiteRequest
                .builder()
                .website("https://raiffeisen_new.ua/")
                .build();

        var updateRequest = UpdateRequest
                .builder()
                .oldValue(secondRequest)
                .newValue(newValue)
                .build();

        var response = (WebsiteResponse) service.addValue(secondRequest);
        var updateResponse = (WebsiteResponse) service.updateValue(updateRequest);

        assertEquals(response.id(), updateResponse.id());
        assertEquals(newValue.website(), updateResponse.website());
    }

    @Test
    @DisplayName("Check exceptions when the request has an incorrect format of the bank's website.")
    void testWedSiteThrowInvalidInputDataException(){
        var request = WebsiteRequest
                .builder()
                .website("privatbank.ua")
                .build();

        assertThrows(InvalidInputDataException.class,
                () -> service.addValue(request)
        );
    }
}
