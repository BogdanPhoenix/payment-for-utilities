package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.service_information_institutions")
@Import(DataBaseConfiguration.class)
public class ServiceInfoEntitiesRequestTestContextConfiguration {
    @Autowired
    private WebsiteServiceImpl websiteService;
    @Autowired
    private EdrpouServiceImpl edrpouService;

    @Lazy
    @Bean(name = "privateBankWebsite")
    public Website privateBankWebsite(){
        return createWebsite(privateBankWebsiteRequest());
    }

    @Lazy
    @Bean(name = "privateBankWebsiteRequest")
    public WebsiteRequest privateBankWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .website("https://privatbank.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankWebsite")
    public Website raiffeisenBankWebsite(){
        return createWebsite(raiffeisenBankWebsiteRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankWebsiteRequest")
    public WebsiteRequest raiffeisenBankWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .website("https://raiffeisen.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "kyivWebsite")
    public Website kyivWebsite(){
        return createWebsite(kyivWebsiteRequest());
    }

    @Lazy
    @Bean(name = "kyivWebsiteRequest")
    public WebsiteRequest kyivWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .website("https://raiffeisen.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateWebsite")
    public Website raiffeisenBankUpdateWebsite(){
        return createWebsite(raiffeisenBankUpdateWebsiteRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateWebsiteRequest")
    public WebsiteRequest raiffeisenBankUpdateWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .website("https://raiffeisen_update.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "privateBankEdrpou")
    public Edrpou privateBankEdrpou(){
        return createEdrpou(privateBankEdrpouRequest());
    }

    @Lazy
    @Bean(name = "privateBankEdrpouRequest")
    public EdrpouRequest privateBankEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .edrpou("14360570")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankEdrpou")
    public Edrpou raiffeisenBankEdrpou(){
        return createEdrpou(raiffeisenBankEdrpouRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankEdrpouRequest")
    public EdrpouRequest raiffeisenBankEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .edrpou("14305909")
                .build();
    }

    @Lazy
    @Bean(name = "kyivEdrpou")
    public Edrpou kyivEdrpou(){
        return createEdrpou(kyivEdrpouRequest());
    }

    @Lazy
    @Bean(name = "kyivEdrpouRequest")
    public EdrpouRequest kyivEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .edrpou("14305919")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateEdrpou")
    public Edrpou raiffeisenBankUpdateEdrpou(){
        return createEdrpou(raiffeisenBankUpdateEdrpouRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateEdrpouRequest")
    public EdrpouRequest raiffeisenBankUpdateEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .edrpou("14380701")
                .build();
    }

    private Website createWebsite(WebsiteRequest websiteRequest){
        var websiteResponse = websiteService.addValue(websiteRequest);
        return websiteService.createEntity(websiteResponse);
    }

    private Edrpou createEdrpou(EdrpouRequest edrpouRequest){
        var edrpouResponse = edrpouService.addValue(edrpouRequest);
        return edrpouService.createEntity(edrpouResponse);
    }
}
