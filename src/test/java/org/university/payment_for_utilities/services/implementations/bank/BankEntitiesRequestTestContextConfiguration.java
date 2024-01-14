package org.university.payment_for_utilities.services.implementations.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.service_information_institutions.Edrpou;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.service_information_institutions.Website;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;
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
    private Website privateBankWebsite;
    @Autowired
    @Qualifier("privateBankEdrpou")
    private Edrpou privateBankEdrpou;
    @Autowired
    @Qualifier("raiffeisenBankWebsite")
    private Website raiffeisenBankWebsite;
    @Autowired
    @Qualifier("raiffeisenBankUpdateEdrpou")
    private Edrpou raiffeisenBankEdrpou;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNum privateBankPhoneNum;
    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNum raiffeisenBankPhoneNum;

    @Lazy
    @Bean(name = "privateBankPhoneNumRequest")
    public BankPhoneNumRequest privateBankPhoneNumRequest(){
        var bank = createBank(privateBankRequest());

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
                .name("Приват Банк")
                .website(privateBankWebsite)
                .edrpou(privateBankEdrpou)
                .mfo("305299")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankRequest")
    public BankRequest raiffeisenBankRequest(){
        return BankRequest
                .builder()
                .name("Raiffeisen Bank")
                .website(raiffeisenBankWebsite)
                .edrpou(raiffeisenBankEdrpou)
                .mfo("380805")
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankPhoneNumRequest")
    public BankPhoneNumRequest raiffeisenBankPhoneNumRequest(){
        var bank = createBank(raiffeisenBankUpdateRequest());

        return BankPhoneNumRequest
                .builder()
                .bank(bank)
                .phoneNum(raiffeisenBankPhoneNum)
                .build();
    }

    @Lazy
    @Bean(name = "raiffeisenBankUpdateRequest")
    public BankRequest raiffeisenBankUpdateRequest(){
        return BankRequest
                .builder()
                .name("Raiffeisen Bank Ukraine")
                .website(raiffeisenBankWebsite)
                .edrpou(raiffeisenBankEdrpou)
                .mfo("305269")
                .build();
    }

    private Bank createBank(BankRequest bankRequest){
        var bankResponse = bankService.addValue(bankRequest);
        return bankService.createEntity(bankResponse);
    }
}
