package org.university.payment_for_utilities.services.implementations.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.pojo.requests.bank.BankPhoneNumRequest;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.bank")
public class BankEntitiesRequestTestContextConfiguration {
    @Autowired
    private BankServiceImpl bankService;

    @Lazy
    @Bean(name = "bankPhoneNumRequest")
    public BankPhoneNumRequest bankPhoneNumRequest(){
        var bankResponse = bankService.addValue(bankRequest());
        var bank = bankService.createEntity(bankResponse);

        return BankPhoneNumRequest
                .builder()
                .bank(bank)
                .phoneNum("380496321563")
                .build();
    }

    @Lazy
    @Bean(name = "privateBankRequest")
    public BankRequest bankRequest(){
        return BankRequest
                .builder()
                .name("Приват Банк")
                .webSite("https://privatbank.ua/")
                .edrpou("14360570")
                .mfo("305299")
                .build();
    }
}
