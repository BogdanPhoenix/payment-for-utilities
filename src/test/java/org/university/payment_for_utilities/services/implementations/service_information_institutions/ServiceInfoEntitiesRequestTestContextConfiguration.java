package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.interfaces.Request;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.PhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.UnitMeasurementRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.pojo.responses.interfaces.Response;
import org.university.payment_for_utilities.services.implementations.CrudServiceAbstract;

import java.lang.reflect.InvocationTargetException;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.service_information_institutions")
@Import(DataBaseConfiguration.class)
public class ServiceInfoEntitiesRequestTestContextConfiguration {
    @Autowired
    private WebsiteServiceImpl websiteService;
    @Autowired
    private EdrpouServiceImpl edrpouService;
    @Autowired
    private UnitMeasurementServiceImpl unitMeasurementService;
    @Autowired
    private PhoneNumServiceImpl phoneNumService;

    @Lazy
    @Bean(name = "companyPhoneNum")
    public PhoneNum companyPhoneNum(){
        var request = companyPhoneNumRequest();
        return createPhoneNum(request);
    }

    @Lazy
    @Bean(name = "companyPhoneNumRequest")
    public PhoneNumRequest companyPhoneNumRequest(){
        return PhoneNumRequest
                .builder()
                .number("380496321573")
                .build();
    }

    @Lazy
    @Bean(name = "bankPhoneNum")
    public PhoneNum bankPhoneNum(){
        var request = bankPhoneNumRequest();
        return createPhoneNum(request);
    }

    @Lazy
    @Bean(name = "bankPhoneNumRequest")
    public PhoneNumRequest bankPhoneNumRequest(){
        return PhoneNumRequest
                .builder()
                .number("380496321563")
                .build();
    }

    @Lazy
    @Bean(name = "updateBankPhoneNum")
    public PhoneNum updateBankPhoneNum(){
        var request = updateBankPhoneNumRequest();
        return createPhoneNum(request);
    }

    @Lazy
    @Bean(name = "updateBankPhoneNumRequest")
    public PhoneNumRequest updateBankPhoneNumRequest(){
        return PhoneNumRequest
                .builder()
                .number("380452136521")
                .build();
    }

    @Lazy
    @Bean(name = "unitKilowatt")
    public UnitMeasurement unitKilowatt(){
        return createUnitMeasurement(unitKilowattRequest());
    }

    @Lazy
    @Bean(name = "unitCubicMeter")
    public UnitMeasurement unitCubicMeter(){
        return createUnitMeasurement(unitCubicMeterRequest());
    }

    @Lazy
    @Bean(name = "unitKilowattRequest")
    public UnitMeasurementRequest unitKilowattRequest(){
        return UnitMeasurementRequest
                .builder()
                .uaName("кіловат")
                .enName("kilowatt")
                .build();
    }

    @Lazy
    @Bean(name = "unitCubicMeterRequest")
    public UnitMeasurementRequest unitCubicMeterRequest(){
        return UnitMeasurementRequest
                .builder()
                .uaName("куб. м.")
                .enName("cubic meter")
                .build();
    }

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

    private PhoneNum createPhoneNum(Request request){
        return (PhoneNum) createEntity(phoneNumService, request);
    }

    private UnitMeasurement createUnitMeasurement(UnitMeasurementRequest unitMeasurementRequest){
        var unitMeasurementResponse = unitMeasurementService.addValue(unitMeasurementRequest);
        return unitMeasurementService.createEntity(unitMeasurementResponse);
    }

    private Website createWebsite(WebsiteRequest websiteRequest){
        var websiteResponse = websiteService.addValue(websiteRequest);
        return websiteService.createEntity(websiteResponse);
    }

    private Edrpou createEdrpou(EdrpouRequest edrpouRequest){
        var edrpouResponse = edrpouService.addValue(edrpouRequest);
        return edrpouService.createEntity(edrpouResponse);
    }

    private Object createEntity(@NonNull CrudServiceAbstract<?, ?> service, Request request){
        var response = service.addValue(request);

        var serviceAbstract = CrudServiceAbstract.class;
        try {
            var method = serviceAbstract.getDeclaredMethod("createEntity", Response.class);
            method.setAccessible(true);
            return method.invoke(service, response);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
