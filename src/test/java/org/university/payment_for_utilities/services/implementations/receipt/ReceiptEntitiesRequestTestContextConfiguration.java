package org.university.payment_for_utilities.services.implementations.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.bank.Bank;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.pojo.requests.receipt.ReceiptRequest;
import org.university.payment_for_utilities.services.implementations.bank.BankEntitiesRequestTestContextConfiguration;
import org.university.payment_for_utilities.services.implementations.user.UserEntitiesRequestTestContextConfiguration;

import java.time.LocalDate;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.receipt")
@Import({
        DataBaseConfiguration.class,
        BankEntitiesRequestTestContextConfiguration.class,
        UserEntitiesRequestTestContextConfiguration.class
})
public class ReceiptEntitiesRequestTestContextConfiguration {
    @Autowired
    @Qualifier("privateBank")
    private Bank privateBank;
    @Autowired
    @Qualifier("raiffeisenBank")
    private Bank raiffeisenBank;
    @Autowired
    @Qualifier("rivneContract")
    private ContractEntity rivneContract;
    @Autowired
    @Qualifier("kyivContract")
    private ContractEntity kyivContract;

    @Lazy
    @Bean(name = "rivneReceiptRequest")
    public ReceiptRequest rivneReceiptRequest() {
        return ReceiptRequest
                .builder()
                .contractEntity(rivneContract)
                .bank(privateBank)
                .billMonth(LocalDate.now())
                .build();
    }

    @Lazy
    @Bean(name = "kyivReceiptRequest")
    public ReceiptRequest kyivReceiptRequest() {
        return ReceiptRequest
                .builder()
                .contractEntity(kyivContract)
                .bank(raiffeisenBank)
                .billMonth(LocalDate.now())
                .build();
    }
}
