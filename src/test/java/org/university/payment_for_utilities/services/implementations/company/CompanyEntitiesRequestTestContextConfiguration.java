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
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.service_information_institutions.UnitMeasurement;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.services.implementations.address.AddressEntitiesRequestTestContextConfiguration;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

import static org.university.payment_for_utilities.AdditionalTestingTools.createEntity;

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
    @Qualifier("addressKyivResidence")
    private AddressResidence addressKyivResidence;
    @Autowired
    @Qualifier("privateBankEdrpou")
    private Edrpou edrpou;
    @Autowired
    @Qualifier("raiffeisenBankEdrpou")
    private Edrpou raiffeisenBankEdrpou;
    @Autowired
    @Qualifier("privateBankWebsite")
    private Website website;
    @Autowired
    @Qualifier("raiffeisenBankWebsite")
    private Website raiffeisenBankWebsite;
    @Autowired
    @Qualifier("unitKilowatt")
    private UnitMeasurement unitKilowatt;
    @Autowired
    @Qualifier("unitCubicMeter")
    private UnitMeasurement unitCubicMeter;
    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNum companyPhoneNum;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNum bankPhoneNum;

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
    @Bean(name = "companyRivneOblenergoPhoneNumRequest")
    public CompanyPhoneNumRequest companyRivneOblenergoPhoneNumRequest(){
        var company = createCompany(companyRivneOblenergoRequest());

        return CompanyPhoneNumRequest
                .builder()
                .company(company)
                .phoneNum(companyPhoneNum)
                .build();
    }

    @Lazy
    @Bean(name = "companyRivneOblenergoRequest")
    public CompanyRequest companyRivneOblenergoRequest(){
        return CompanyRequest
                .builder()
                .address(addressResidence)
                .edrpou(edrpou)
                .website(website)
                .name("Рівне ОблЕнерго")
                .currentAccount("96410247789652")
                .build();
    }

    @Lazy
    @Bean(name = "companyKyivOblenergoPhoneNumRequest")
    public CompanyPhoneNumRequest companyKyivOblenergoPhoneNumRequest(){
        var company = createCompany(companyKyivOblenergoRequest());

        return CompanyPhoneNumRequest
                .builder()
                .company(company)
                .phoneNum(bankPhoneNum)
                .build();
    }

    @Lazy
    @Bean(name = "companyKyivOblenergoRequest")
    public CompanyRequest companyKyivOblenergoRequest(){
        return CompanyRequest
                .builder()
                .address(addressKyivResidence)
                .edrpou(raiffeisenBankEdrpou)
                .website(raiffeisenBankWebsite)
                .name("Київ ОблЕнерго")
                .currentAccount("41203654129521")
                .build();
    }

    private Company createCompany(CompanyRequest request) {
        return (Company) createEntity(companyService, request);
    }
}
