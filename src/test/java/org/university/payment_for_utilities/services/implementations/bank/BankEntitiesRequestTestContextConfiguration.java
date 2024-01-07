package org.university.payment_for_utilities.services.implementations.bank;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.pojo.requests.bank.BankRequest;

@TestConfiguration
public class BankEntitiesRequestTestContextConfiguration {
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
