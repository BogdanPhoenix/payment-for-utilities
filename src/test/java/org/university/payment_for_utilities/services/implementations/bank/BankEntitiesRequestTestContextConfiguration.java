package org.university.payment_for_utilities.services.implementations.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
import org.university.payment_for_utilities.pojo.responses.bank.BankResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.EdrpouResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.WebsiteResponse;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.bank")
@Import({
        DataBaseConfiguration.class,
        ServiceInfoEntitiesRequestTestContextConfiguration.class
})
public class BankEntitiesRequestTestContextConfiguration {
    @Autowired
    private BankServiceImpl bankService;
    @Autowired
    @Qualifier("privateBankWebsite")
    private WebsiteResponse privateBankWebsite;
    @Autowired
    @Qualifier("privateBankEdrpou")
    private EdrpouResponse privateBankEdrpou;
    @Autowired
    @Qualifier("raiffeisenBankWebsite")
    private WebsiteResponse raiffeisenBankWebsite;
    @Autowired
    @Qualifier("raiffeisenBankUpdateEdrpou")
    private EdrpouResponse raiffeisenBankEdrpou;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNumResponse privateBankPhoneNum;
    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNumResponse raiffeisenBankPhoneNum;

    @Lazy
    @Bean(name = "privateBank")
    public BankResponse privateBank() {
        return createBank(privateBankRequest());
    }

    @Lazy
    @Bean(name = "privateBankPhoneNumRequest")
    public BankPhoneNumRequest privateBankPhoneNumRequest(){
        var bank = privateBank();

        return BankPhoneNumRequest
                .builder()
                .bank(bank)
                .phoneNum(privateBankPhoneNum)
                .build();
    }

    @Lazy
    @Bean(name = "privateBankRequest")
    public BankRequest privateBankRequest(){
        return BankRequest
                .builder()
                .uaName("Приват Банк")
                .enName("Private Bank")
                .website(privateBankWebsite)
                .edrpou(privateBankEdrpou)
                .mfo("305299")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBank")
    public BankResponse raiffeisenBank() {
        return createBank(raiffeisenBankRequest());
    }

    @Lazy
    @Bean(name = "raiffeisenBankPhoneNumRequest")
    public BankPhoneNumRequest raiffeisenBankPhoneNumRequest(){
        var bank = raiffeisenBank();

        return BankPhoneNumRequest
                .builder()
                .bank(bank)
                .phoneNum(raiffeisenBankPhoneNum)
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankRequest")
    public BankRequest raiffeisenBankRequest(){
        return BankRequest
                .builder()
                .uaName("Райффайзен Банк")
                .enName("Raiffeisen Bank")
                .website(raiffeisenBankWebsite)
                .edrpou(raiffeisenBankEdrpou)
                .mfo("380805")
                .build();
    }

    private BankResponse createBank(BankRequest request){
        return (BankResponse) bankService.addValue(request);
    }
}
