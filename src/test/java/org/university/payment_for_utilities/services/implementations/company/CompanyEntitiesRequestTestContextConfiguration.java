package org.university.payment_for_utilities.services.implementations.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.pojo.requests.company.CompanyPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.company.CompanyRequest;
import org.university.payment_for_utilities.pojo.requests.company.CompanyTariffRequest;
import org.university.payment_for_utilities.pojo.requests.company.TypeOfferRequest;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.company.CompanyResponse;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;
import org.university.payment_for_utilities.pojo.responses.company.TypeOfferResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.UnitMeasurementResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;
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
    private CompanyTariffServiceImpl companyTariffService;
    @Autowired
    private TypeOfferServiceImpl typeOfferService;

    @Autowired
    @Qualifier("addressResidence")
    private AddressResidenceResponse addressResidence;
    @Autowired
    @Qualifier("addressKyivResidence")
    private AddressResidenceResponse addressKyivResidence;
    @Autowired
    @Qualifier("privateBankEdrpou")
    private EdrpouResponse edrpou;
    @Autowired
    @Qualifier("raiffeisenBankEdrpou")
    private EdrpouResponse raiffeisenBankEdrpou;
    @Autowired
    @Qualifier("privateBankWebsite")
    private WebsiteResponse website;
    @Autowired
    @Qualifier("raiffeisenBankWebsite")
    private WebsiteResponse raiffeisenBankWebsite;
    @Autowired
    @Qualifier("unitKilowatt")
    private UnitMeasurementResponse unitKilowatt;
    @Autowired
    @Qualifier("unitCubicMeter")
    private UnitMeasurementResponse unitCubicMeter;
    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNumResponse companyPhoneNum;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNumResponse bankPhoneNum;

    @Lazy
    @Bean(name = "createRivneTariff")
    public CompanyTariffResponse createRivneTariff() {
        return createCompanyTariff(createRivneTariffRequest());
    }

    @Lazy
    @Bean(name = "createKyivTariff")
    public CompanyTariffResponse createKyivTariff() {
        return createCompanyTariff(createKyivTariffRequest());
    }

    @Lazy
    @Bean(name = "createRivneTariffRequest")
    public CompanyTariffRequest createRivneTariffRequest() {
        var company = createCompany(companyRivneOblenergoRequest());
        var type = createTypeOffer(typeOfferGasRequest());

        return CompanyTariffRequest
                .builder()
                .company(company)
                .type(type)
                .name("Денний")
                .fixedCost("13.5")
                .build();
    }

    @Lazy
    @Bean(name = "createKyivTariffRequest")
    public CompanyTariffRequest createKyivTariffRequest() {
        var company = createCompany(companyKyivOblenergoRequest());
        var type = createTypeOffer(typeOfferElectricRequest());

        return CompanyTariffRequest
                .builder()
                .company(company)
                .type(type)
                .name("Нічний")
                .fixedCost("1.68")
                .build();

    }

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
    @Bean(name = "typeOfferUpdate")
    public TypeOfferResponse typeOfferUpdate() {
        return (TypeOfferResponse) typeOfferService.addValue(typeOfferUpdateRequest());
    }

    @Lazy
    @Bean(name = "typeOfferUpdateRequest")
    public TypeOfferRequest typeOfferUpdateRequest(){
        return TypeOfferRequest
                .builder()
                .unitMeasurement(unitKilowatt)
                .uaName("Оновленні дані")
                .enName("Update data")
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
                .uaName("Рівне ОблЕнерго")
                .enName("Rivne Oblenergo")
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
                .uaName("Київ ОблЕнерго")
                .enName("Kyiv Oblenergo")
                .currentAccount("41203654129521")
                .build();
    }

    private CompanyResponse createCompany(CompanyRequest request) {
        return (CompanyResponse) companyService.addValue(request);
    }

    private CompanyTariffResponse createCompanyTariff(CompanyTariffRequest request) {
        return (CompanyTariffResponse) companyTariffService.addValue(request);
    }

    private TypeOfferResponse createTypeOffer(TypeOfferRequest request) {
        return (TypeOfferResponse) typeOfferService.addValue(request);
    }
}
