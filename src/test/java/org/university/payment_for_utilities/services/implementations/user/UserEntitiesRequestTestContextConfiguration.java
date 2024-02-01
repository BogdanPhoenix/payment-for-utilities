package org.university.payment_for_utilities.services.implementations.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.domains.company.CompanyTariff;
import org.university.payment_for_utilities.domains.service_information_institutions.PhoneNum;
import org.university.payment_for_utilities.domains.user.ContractEntity;
import org.university.payment_for_utilities.domains.user.RegisteredUser;
import org.university.payment_for_utilities.enumarations.Role;
import org.university.payment_for_utilities.pojo.requests.user.*;
import org.university.payment_for_utilities.pojo.responses.user.UserResponse;
import org.university.payment_for_utilities.services.implementations.company.CompanyEntitiesRequestTestContextConfiguration;
import org.university.payment_for_utilities.services.implementations.service_information_institutions.ServiceInfoEntitiesRequestTestContextConfiguration;

import static org.university.payment_for_utilities.AdditionalTestingTools.createEntity;

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
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("companyPhoneNum")
    private PhoneNum ivanPhoneNumber;
    @Autowired
    @Qualifier("bankPhoneNum")
    private PhoneNum olegPhoneNumber;
    @Autowired
    @Qualifier("createRivneTariff")
    private CompanyTariff rivneTariff;
    @Autowired
    @Qualifier("createKyivTariff")
    private CompanyTariff kyivTariff;

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
    public ContractEntity rivneContract() {
        return createContractEntity(createRivneContractRequest());
    }

    @Lazy
    @Bean(name = "createRivneContractRequest")
    public ContractEntityRequest createRivneContractRequest() {
        var registered = createRegisteredUser(registeredUserIvanRequest());

        return ContractEntityRequest
                .builder()
                .registeredUser(registered)
                .companyTariff(rivneTariff)
                .numContract("14231402132")
                .build();
    }

    @Lazy
    @Bean(name = "userIvanRequest")
    public InfoAboutUserRequest userIvanRequest(){
        var registered = createRegisteredUser(registeredUserIvanRequest());
        return InfoAboutUserRequest
                .builder()
                .registered(registered)
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
                .phoneNum(ivanPhoneNumber)
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
    public ContractEntity kyivContract() {
        return createContractEntity(createKyivContractRequest());
    }

    @Lazy
    @Bean(name = "createKyivContractRequest")
    public ContractEntityRequest createKyivContractRequest() {
        var registered = createRegisteredUser(registeredUserOlegRequest());

        return ContractEntityRequest
                .builder()
                .registeredUser(registered)
                .companyTariff(kyivTariff)
                .numContract("54231024692")
                .build();
    }

    @Lazy
    @Bean(name = "userOlegRequest")
    public InfoAboutUserRequest userOlegRequest(){
        var registered = createRegisteredUser(registeredUserOlegRequest());

        return InfoAboutUserRequest
                .builder()
                .registered(registered)
                .firstName("Oleg")
                .lastName("Nick")
                .build();
    }

    @Lazy
    @Bean(name = "registeredUserOlegRequest")
    public RegisteredUserRequest registeredUserOlegRequest(){
        return RegisteredUserRequest
                .builder()
                .username("oleg@ukr.net")
                .password("qWerty5iop$@")
                .role(Role.ADMIN)
                .phoneNum(olegPhoneNumber)
                .build();
    }

    private RegisteredUser createRegisteredUser(RegisteredUserRequest request) {
        registerUserService.registration(request);
        var response = (UserResponse) registerUserService.getByUsername(request.getUsername());

        return RegisteredUser
                .builder()
                .id(response.getId())
                .username(response.getUsername())
                .role(response.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNum(response.getPhoneNum())
                .createDate(response.getCreateDate())
                .updateDate(response.getUpdateDate())
                .build();
    }

    private ContractEntity createContractEntity(ContractEntityRequest request) {
        return (ContractEntity) createEntity(contractEntityService, request);
    }
}
