package org.university.payment_for_utilities.services.implementations.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.requests.user.InfoAboutUserRequest;
import org.university.payment_for_utilities.pojo.requests.user.RegisteredUserRequest;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

import static org.university.payment_for_utilities.AdditionalTestingTools.createEntity;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.user")
@Import({
        DataBaseConfiguration.class,
        ServiceInfoEntitiesRequestTestContextConfiguration.class
})
public class UserEntitiesRequestTestContextConfiguration {
    @Autowired
    private RegisterUserServiceImpl registerUserService;

    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNum ivanPhoneNumber;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNum olegPhoneNumber;

    @Lazy
    @Bean(name = "userIvanRequest")
    public InfoAboutUserRequest userIvanRequest(){
        var registered = createRegisteredUser(registeredUserIvanRequest());

        return InfoAboutUserRequest
                .builder()
                .registered(registered)
                .role(Role.USER)
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
    }

    @Lazy
    @Bean(name = "userOlegRequest")
    public InfoAboutUserRequest userOlegRequest(){
        var registered = createRegisteredUser(registeredUserOlegRequest());

        return InfoAboutUserRequest
                .builder()
                .registered(registered)
                .role(Role.ADMIN)
                .firstName("Oleg")
                .lastName("Nick")
                .build();
    }

    @Lazy
    @Bean(name = "registeredUserIvanRequest")
    public RegisteredUserRequest registeredUserIvanRequest(){
        return RegisteredUserRequest
                .builder()
                .userEmail("test@gmail.com")
                .passwordUser("qwerTy4iop$")
                .phoneNum(ivanPhoneNumber)
                .build();
    }

    @Lazy
    @Bean(name = "registeredUserOlegRequest")
    public RegisteredUserRequest registeredUserOlegRequest(){
        return RegisteredUserRequest
                .builder()
                .userEmail("oleg@ukr.net")
                .passwordUser("qWerty5iop$@")
                .phoneNum(olegPhoneNumber)
                .build();
    }

    private RegisteredUser createRegisteredUser(RegisteredUserRequest request) {
        return (RegisteredUser) createEntity(registerUserService, request);
    }
}
