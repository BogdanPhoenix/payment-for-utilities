package org.university.payment_for_utilities.services.implementations.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.address.AddressResidence;
import org.university.payment_for_utilities.domains.company.Company;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.services.implementations.address.AddressEntitiesRequestTestContextConfiguration;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.company")
@Import({
        DataBaseConfiguration.class,
        AddressEntitiesRequestTestContextConfiguration.class,
        ServiceInfoEntitiesRequestTestContextConfiguration.class
})
public class CompanyEntitiesRequestTestContextConfiguration {
    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    @Qualifier("addressResidence")
    private AddressResidence addressResidence;
    @Autowired
    @Qualifier("privateBankEdrpou")
    private Edrpou edrpou;
    @Autowired
    @Qualifier("privateBankWebsite")
    private Website website;
    @Autowired
    @Qualifier("unitKilowatt")
    private UnitMeasurement unitKilowatt;
    @Autowired
    @Qualifier("unitCubicMeter")
    private UnitMeasurement unitCubicMeter;

    @Lazy
    @Bean(name = "typeOfferGasRequest")
    public TypeOfferRequest typeOfferGasRequest(){
        return TypeOfferRequest
                .builder()
                .unitMeasurement(unitCubicMeter)
                .uaName("Денний")
                .enName("Daytime")
                .build();
    }

    @Lazy
    @Bean(name = "typeOfferElectricRequest")
    public TypeOfferRequest typeOfferElectricRequest(){
        return TypeOfferRequest
                .builder()
                .unitMeasurement(unitKilowatt)
                .uaName("Цілодобовий")
                .enName("All day")
                .build();
    }

    @Lazy
    @Bean(name = "companyPhoneNumRequest")
    public CompanyPhoneNumRequest companyPhoneNumRequest(){
        var company = createCompany(companyRequest());

        return CompanyPhoneNumRequest
                .builder()
                .company(company)
                .phoneNum("380496321573")
                .build();
    }

    @Lazy
    @Bean(name = "companyRequest")
    public CompanyRequest companyRequest(){
        return CompanyRequest
                .builder()
                .address(addressResidence)
                .edrpou(edrpou)
                .website(website)
                .name("Рівне ОблЕнерго")
                .currentAccount("96410247789652")
                .build();
    }

    private Company createCompany(CompanyRequest request) {
        var companyResponse = companyService.addValue(request);
        return companyService.createEntity(companyResponse);
    }
}
