package org.university.payment_for_utilities.services.implementations.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.requests.user.*;
import org.university.payment_for_utilities.pojo.responses.company.CompanyTariffResponse;
import org.university.payment_for_utilities.pojo.responses.service_information_institutions.PhoneNumResponse;
import org.university.payment_for_utilities.pojo.responses.user.ContractEntityResponse;
import org.university.payment_for_utilities.pojo.responses.user.UserResponse;
import org.university.payment_for_utilities.services.implementations.company.CompanyEntitiesRequestTestContextConfiguration;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.user")
@Import({
        DataBaseConfiguration.class,
        CompanyEntitiesRequestTestContextConfiguration.class,
        ServiceInfoEntitiesRequestTestContextConfiguration.class
})
public class UserEntitiesRequestTestContextConfiguration {
    @Autowired
    private RegisteredUserServiceImpl registerUserService;
    @Autowired
    private ContractEntityServiceImpl contractEntityService;

    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNumResponse ivanPhoneNumber;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNumResponse olegPhoneNumber;
    @Autowired
    @Qualifier("createRivneTariff")
    private CompanyTariffResponse rivneTariff;
    @Autowired
    @Qualifier("createKyivTariff")
    private CompanyTariffResponse kyivTariff;

    @Lazy
    @Bean(name = "changePasswordRequest")
    public ChangePasswordRequest changePasswordRequest() {
        return ChangePasswordRequest
                .builder()
                .currentPassword("qwerTy4iop$")
                .newPassword("qWerty5iop$@")
                .confirmationPassword("qWerty5iop$@")
                .build();
    }

    @Lazy
    @Bean(name = "rivneContract")
    public ContractEntityResponse rivneContract() {
        return createContractEntity(createRivneContractRequest());
    }

    @Lazy
    @Bean(name = "createRivneContractRequest")
    public ContractEntityRequest createRivneContractRequest() {
        var registered = createRegisteredUser(registeredUserIvanRequest());

        return ContractEntityRequest
                .builder()
                .registeredUser(registered.getId())
                .companyTariff(rivneTariff.getId())
                .numContract("14231402132")
                .build();
    }

    @Lazy
    @Bean(name = "userIvanRequest")
    public InfoAboutUserRequest userIvanRequest(){
        var registered = createRegisteredUser(registeredUserIvanRequest());
        return InfoAboutUserRequest
                .builder()
                .registered(registered.getId())
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
    }

    @Lazy
    @Bean(name = "registeredUserIvanRequest")
    public RegisteredUserRequest registeredUserIvanRequest(){
        return RegisteredUserRequest
                .builder()
                .username("test@gmail.com")
                .password("qwerTy4iop$")
                .role(Role.USER)
                .phoneNum(ivanPhoneNumber.getId())
                .build();
    }

    @Lazy
    @Bean(name = "authenticationUserIvanRequest")
    public AuthenticationRequest authenticationUserIvanRequest() {
        return AuthenticationRequest
                .builder()
                .username("test@gmail.com")
                .password("qwerTy4iop$")
                .build();
    }

    @Lazy
    @Bean(name = "kyivContract")
    public ContractEntityResponse kyivContract() {
        return createContractEntity(createKyivContractRequest());
    }

    @Lazy
    @Bean(name = "createKyivContractRequest")
    public ContractEntityRequest createKyivContractRequest() {
        var registered = registeredUserOlegResponse();

        return ContractEntityRequest
                .builder()
                .registeredUser(registered.getId())
                .companyTariff(kyivTariff.getId())
                .numContract("54231024692")
                .build();
    }

    @Lazy
    @Bean(name = "userOlegRequest")
    public InfoAboutUserRequest userOlegRequest(){
        var registered = registeredUserOlegResponse();

        return InfoAboutUserRequest
                .builder()
                .registered(registered.getId())
                .firstName("Oleg")
                .lastName("Nick")
                .build();
    }

    @Lazy
    @Bean(name = "registeredUserOlegResponse")
    public UserResponse registeredUserOlegResponse() {
        return createRegisteredUser(registeredUserOlegRequest());
    }

    @Lazy
    @Bean(name = "registeredUserOlegRequest")
    public RegisteredUserRequest registeredUserOlegRequest(){
        return RegisteredUserRequest
                .builder()
                .username("oleg@ukr.net")
                .password("qWerty5iop$@")
                .role(Role.ADMIN)
                .phoneNum(olegPhoneNumber.getId())
                .build();
    }

    private @NonNull UserResponse createRegisteredUser(RegisteredUserRequest request) {
        registerUserService.registration(request);
        return (UserResponse) registerUserService.getByUsername(request.getUsername());
    }

    private ContractEntityResponse createContractEntity(ContractEntityRequest request) {
        return (ContractEntityResponse) contractEntityService.addValue(request);
    }
}
