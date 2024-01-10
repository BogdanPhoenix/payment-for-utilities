package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.exceptions.InvalidInputDataException;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;
import org.university.payment_for_utilities.pojo.update_request.UpdateRequest;
import org.university.payment_for_utilities.repositories.service_information_institutions.WebsiteRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;

import java.util.Optional;

@Slf4j
@Service
public class WebsiteServiceImpl extends CrudServiceAbstract<Website, WebsiteRepository> implements WebsiteService {
    private static final String WEBSITE_TEMPLATE = "^(http|https)://.+";

    protected WebsiteServiceImpl(WebsiteRepository repository) {
        super(repository, "Websites");
    }

    @Override
    protected Website createEntity(Request request) {
        var websiteRequest = (WebsiteRequest) request;
        return Website
                .builder()
                .website(websiteRequest.getWebsite())
                .currentData(true)
                .build();
    }

    @Override
    protected Website createEntity(Response response) {
        var websiteResponse = (WebsiteResponse) response;
        return Website
                .builder()
                .id(websiteResponse.getId())
                .website(websiteResponse.getWebsite())
                .currentData(true)
                .build();
    }

    @Override
    protected Response createResponse(@NonNull Website entity) {
        return WebsiteResponse
                .builder()
                .id(entity.getId())
                .website(entity.getWebsite())
                .build();
    }

    @Override
    protected void updateEntity(@NonNull Website entity, @NonNull UpdateRequest updateRequest) {
        var oldValue = (WebsiteRequest) updateRequest.getOldValue();
        var newValue = (WebsiteRequest) updateRequest.getNewValue();

        entity.setWebsite(
                updateAttribute(
                        oldValue.getWebsite(),
                        newValue.getWebsite()
                )
        );
    }

    @Override
    protected void validationProcedureAddValue(@NonNull Request request) throws InvalidInputDataException {
        var websiteRequest = (WebsiteRequest) request;
        validateWebSite(websiteRequest.getWebsite());
    }

    @Override
    protected void validationProcedureValidateUpdate(@NonNull UpdateRequest updateRequest) throws InvalidInputDataException {
        var oldValue = (WebsiteRequest) updateRequest.getOldValue();
        var newValue = (WebsiteRequest) updateRequest.getNewValue();

        validateWebSite(oldValue.getWebsite());
        validateWebSite(newValue.getWebsite());
    }

    private void validateWebSite(@NonNull String webSite) throws InvalidInputDataException {
        if (isWebSite(webSite)){
            return;
        }

        var message = String.format("The link you provided: \"%s\" to the bank's website was not validated. It must match the following template: \"%s\".", webSite, WEBSITE_TEMPLATE);
        log.error(message);
        throw new InvalidInputDataException(message);
    }

    private boolean isWebSite(@NonNull String webSite){
        return webSite.isEmpty() ||
                webSite.toLowerCase()
                        .matches(WEBSITE_TEMPLATE);
    }

    @Override
    protected Optional<Website> findOldEntity(@NonNull Request request) {
        var websiteRequest = (WebsiteRequest) request;
        return repository
                .findByWebsite(
                        websiteRequest.getWebsite()
                );
    }
}
