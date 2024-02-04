package org.university.payment_for_utilities.services.implementations.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.EdrpouRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.PhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.UnitMeasurementRequest;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.UnitMeasurementResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;

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
    public PhoneNumResponse companyPhoneNum(){
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
    public PhoneNumResponse bankPhoneNum(){
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
    public PhoneNumResponse updateBankPhoneNum(){
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
    public UnitMeasurementResponse unitKilowatt(){
        return createUnitMeasurement(unitKilowattRequest());
    }

    @Lazy
    @Bean(name = "unitCubicMeter")
    public UnitMeasurementResponse unitCubicMeter(){
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
    public WebsiteResponse privateBankWebsite(){
        return createWebsite(privateBankWebsiteRequest());
    }

    @Lazy
    @Bean(name = "privateBankWebsiteRequest")
    public WebsiteRequest privateBankWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .value("https://privatbank.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankWebsite")
    public WebsiteResponse raiffeisenBankWebsite(){
        return createWebsite(raiffeisenBankWebsiteRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankWebsiteRequest")
    public WebsiteRequest raiffeisenBankWebsiteRequest(){
        return WebsiteRequest
                .builder()
                .value("https://raiffeisen.ua/")
                .build();
    }

    @Lazy
    @Bean(name = "privateBankEdrpou")
    public EdrpouResponse privateBankEdrpou(){
        return createEdrpou(privateBankEdrpouRequest());
    }

    @Lazy
    @Bean(name = "privateBankEdrpouRequest")
    public EdrpouRequest privateBankEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .value("14360570")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankEdrpou")
    public EdrpouResponse raiffeisenBankEdrpou(){
        return createEdrpou(raiffeisenBankEdrpouRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankEdrpouRequest")
    public EdrpouRequest raiffeisenBankEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .value("14305909")
                .build();
    }

    @Lazy
    @Bean(name = "kyivEdrpou")
    public EdrpouResponse kyivEdrpou(){
        return createEdrpou(kyivEdrpouRequest());
    }

    @Lazy
    @Bean(name = "kyivEdrpouRequest")
    public EdrpouRequest kyivEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .value("14305919")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateEdrpou")
    public EdrpouResponse raiffeisenBankUpdateEdrpou(){
        return createEdrpou(raiffeisenBankUpdateEdrpouRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateEdrpouRequest")
    public EdrpouRequest raiffeisenBankUpdateEdrpouRequest(){
        return EdrpouRequest
                .builder()
                .value("14380701")
                .build();
    }

    private PhoneNumResponse createPhoneNum(PhoneNumRequest request){
        return (PhoneNumResponse) phoneNumService.addValue(request);
    }

    private UnitMeasurementResponse createUnitMeasurement(UnitMeasurementRequest request){
        return (UnitMeasurementResponse) unitMeasurementService.addValue(request);
    }

    private WebsiteResponse createWebsite(WebsiteRequest request){
        return (WebsiteResponse) websiteService.addValue(request);
    }

    private EdrpouResponse createEdrpou(EdrpouRequest request){
        return (EdrpouResponse) edrpouService.addValue(request);
    }
}
