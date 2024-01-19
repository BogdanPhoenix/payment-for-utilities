package org.university.payment_for_utilities.services.implementations.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.user")
@Import(ServiceInfoEntitiesRequestTestContextConfiguration.class)
public class UserEntitiesRequestTestContextConfiguration {
    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNum ivanPhoneNumber;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNum olegPhoneNumber;

    @Lazy
    @Bean(name = "userIvanRequest")
    public RegisteredUserRequest userIvanRequest(){
        return RegisteredUserRequest
                .builder()
                .userEmail("test@gmail.com")
                .passwordUser("qwerTy4iop$")
                .phoneNum(ivanPhoneNumber)
                .build();
    }

    @Lazy
    @Bean(name = "userOlegRequest")
    public RegisteredUserRequest userOlegRequest(){
        return RegisteredUserRequest
                .builder()
                .userEmail("oleg@ukr.net")
                .passwordUser("qWerty5iop$@")
                .phoneNum(olegPhoneNumber)
                .build();
    }
}
