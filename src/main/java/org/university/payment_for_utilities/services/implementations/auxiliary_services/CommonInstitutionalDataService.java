package org.university.payment_for_utilities.services.implementations.auxiliary_services;

import lombok.NonNull;
import org.university.payment_for_utilities.domains.abstract_class.CommonInstitutionalData;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.abstract_class.CommonInstitutionalDataRequest;
import org.university.payment_for_utilities.pojo.requests.abstract_class.Request;
import org.university.payment_for_utilities.pojo.responses.abstract_class.Response;
import org.university.payment_for_utilities.repositories.TableSearcherRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.EdrpouRepository;
import org.university.payment_for_utilities.repositories.service_information_institutions.WebsiteRepository;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.EdrpouService;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;
import org.university.payment_for_utilities.domains.abstract_class.CommonInstitutionalData.CommonInstitutionalDataBuilder;

public abstract class CommonInstitutionalDataService<T extends CommonInstitutionalData, J extends TableSearcherRepository<T>> extends TransliterationService<T, J> {
    private final EdrpouService edrpouService;
    private final EdrpouRepository edrpouRepository;
    private final WebsiteService websiteService;
    private final WebsiteRepository websiteRepository;

    protected CommonInstitutionalDataService(
            J repository,
            String tableName,
            EdrpouService edrpouService,
            EdrpouRepository edrpouRepository,
            WebsiteService websiteService,
            WebsiteRepository websiteRepository
    ) {
        super(repository, tableName);

        this.edrpouService = edrpouService;
        this.edrpouRepository = edrpouRepository;
        this.websiteService = websiteService;
        this.websiteRepository = websiteRepository;
    }

    protected <B extends CommonInstitutionalDataBuilder<?, ?>> B initCommonInstitutionalDataBuilder(@NonNull B builder, @NonNull CommonInstitutionalDataRequest request) {
        var website = getWebsite(request.getWebsite());
        var edrpou = getEdrpou(request.getEdrpou());

        super
                .initTransliterationPropertyBuilder(builder, request)
                .edrpou(edrpou)
                .website(website);
        return builder;
    }

    @Override
    protected void deactivatedChildren(@NonNull T entity) {
        edrpouService.removeValue(entity.getEdrpou().getId());
        websiteService.removeValue(entity.getWebsite().getId());
    }

    @Override
    protected void updateEntity(@NonNull T entity, @NonNull Request request) {
        super.updateEntity(entity, request);
        var newValue = (CommonInstitutionalDataRequest) request;

        if(!newValue.getWebsite().equals(Response.EMPTY_PARENT_ENTITY)){
            var website = getWebsite(newValue.getWebsite());
            entity.setWebsite(website);
        }
        if(!newValue.getEdrpou().equals(Response.EMPTY_PARENT_ENTITY)){
            var edrpou = getEdrpou(newValue.getEdrpou());
            entity.setEdrpou(edrpou);
        }
    }

    protected @NonNull Website getWebsite(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(websiteRepository, id);
    }

    protected @NonNull Edrpou getEdrpou(@NonNull Long id) {
        return CrudServiceAbstract
                .getEntity(edrpouRepository, id);
    }
}
